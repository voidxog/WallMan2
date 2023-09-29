package com.colorata.wallman.core.ui.components

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.colorata.animateaslifestyle.isCompositionLaunched
import com.colorata.wallman.core.data.animation
import com.colorata.wallman.core.ui.LightDarkPreview
import com.colorata.wallman.core.ui.theme.WallManPreviewTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.Language
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

@Language("AGSL")
private val SHADER = """
    uniform vec2 iResolution;
    uniform float iTime;
    uniform shader in_shader;
    layout(color) uniform vec4 iSparkleColor;
    
    const float PI = 3.1415926535897932384626;

    float triangleNoise(vec2 n) {
        n = fract(n * vec2(5.3987, 5.4421));
        n += dot(n.yx, n.xy + vec2(21.5351, 14.3137));
        float xy = n.x * n.y;
        return fract(xy * 95.4307) + fract(xy * 75.04961) - 1.0;
    }

    float threshold(float v, float l, float h) {
        return step(l, v) * (1.0 - step(h, v));
    }

    float sparkles(vec2 uv, float t) {
        float n = triangleNoise(uv);
        float sparkleIntensity = 0.0;
        for (float i = 0.0; i < 2.0; i += 1.0) {
            float l = i * 0.1;
            float h = l + 0.05;
            float o = sin(PI * (t + 0.35 * i));
            sparkleIntensity += threshold(n + o, l, h);
        }
        return sparkleIntensity * 1.0;
      }

    float coerse(float down, float up, float value) {
        return max(min(value, up), down);
    }

    float radius() {
        return min(iResolution.x, iResolution.y) * 0.35;
    }

    float circleAlpha(vec2 uv, float targetDistance) {
        float delta = radius();
        vec2 center = vec2(iResolution.x / 2.0, iResolution.y / 2.0);
        float cx = center.x - uv.x;
        float cy = center.y - uv.y;
        float distance = sqrt(cx * cx + cy * cy);
        
        float uvDistance = (coerse(targetDistance - delta, targetDistance + delta, distance) - targetDistance + delta) / (2.0 * delta);
        
        return 1.0 - cos(2.0 * PI * uvDistance);
    }
    
    vec4 main(vec2 fragCoord) {
      vec2 uv = fragCoord.xy;
      float alpha = sparkles(uv, cos(iTime / 20.0)) / 3.0;
      float cAlpha = circleAlpha(uv, iTime * 3.5 * radius() - radius());
      alpha = alpha * cAlpha;
      
      return mix(in_shader.eval(uv), iSparkleColor, alpha);
    }
""".trimIndent()

@Suppress("UNCHECKED_CAST")
private class UpdateEffectState(
    private val scope: CoroutineScope,
    private val effect: () -> Color,
    private val animationSpec: () -> AnimationSpec<*>,
    private val size: () -> Size
) {
    private val _radius = Animatable(0f)
    val radius: Float
        get() = _radius.value

    private val _effectColor = Animatable(Color.Transparent)
    private val effectColor: Color
        get() = _effectColor.value

    fun update() {
        scope.launch {
            if (_effectColor.value != Color.Transparent) {
                _effectColor.animateTo(Color.Transparent, tween(500))
            }
            _radius.snapTo(0f)
            launch {
                _effectColor.animateTo(effect())
            }
            _radius.animateTo(
                calculateUVRadius(size()),
                animationSpec() as AnimationSpec<Float>
            )
            _radius.snapTo(0f)
            _effectColor.snapTo(Color.Transparent)
        }
    }
}

@Composable
private fun rememberUpdateEffectState(key: Any?, size: Size, effectColor: Color, animationSpec: AnimationSpec<*>): UpdateEffectState {
    val scope = rememberCoroutineScope()
    val state = remember {
        UpdateEffectState(scope, effect = {
            effectColor
        }, size = {
            size
        }, animationSpec = {
            animationSpec
        })
    }
    val firstComposition = isCompositionLaunched()
    LaunchedEffect(key) {
        if (!firstComposition) return@LaunchedEffect
        state.update()
    }
    return state
}

@Composable
fun UpdateEffect(
    key: Any?,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    effectColor: Color = MaterialTheme.colorScheme.primary,
    animationSpec: AnimationSpec<Float> = MaterialTheme.animation.emphasized(MaterialTheme.animation.durationSpec.extraLong4 * 3)
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        UpdateEffectApi33(
            key = key,
            modifier = modifier,
            containerColor = containerColor,
            effectColor = effectColor,
            animationSpec = animationSpec
        )
    } else {
        UpdateEffectApi32(
            key = key,
            modifier = modifier,
            containerColor = containerColor,
            effectColor = effectColor,
            animationSpec = animationSpec
        )
    }
}

@RequiresApi(33)
@Composable
private fun UpdateEffectApi33(
    key: Any?,
    modifier: Modifier = Modifier,
    containerColor: Color,
    effectColor: Color,
    animationSpec: AnimationSpec<Float>
) {
    val shader = remember { RuntimeShader(SHADER) }
    val time = remember { Animatable(0.0f) }
    var size by remember { mutableStateOf(Size(500f, 500f)) }
    val updatedEffectColor = remember { Animatable(Color.Transparent) }
    val firstComposition = isCompositionLaunched()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key) {
        if (!firstComposition) return@LaunchedEffect
        scope.launch {
            if (updatedEffectColor.value != Color.Transparent) {
                updatedEffectColor.animateTo(Color.Transparent, tween(500))
            }
            time.snapTo(0f)
            launch {
                updatedEffectColor.animateTo(effectColor)
            }
            time.animateTo(
                calculateUVRadius(size),
                animationSpec
            )
            time.snapTo(0f)
            updatedEffectColor.snapTo(Color.Transparent)
        }
    }
    Box(
        modifier
            .graphicsLayer {
                clip = true
                with(shader) {
                    setFloatUniform("iTime", time.value)
                    setFloatUniform("iResolution", size.width, size.height)
                    setColorUniform("iSparkleColor", updatedEffectColor.value.toArgb())
                }
                renderEffect =
                    RenderEffect
                        .createRuntimeShaderEffect(shader, "in_shader")
                        .asComposeRenderEffect()
            }
            .background(containerColor)
            .onSizeChanged {
                size = it.toSize()
            }) {
        Text(time.value.toString())
    }
}

@Stable
private fun calculateUVRadius(size: Size): Float {
    return sqrt((size.width / size.height).pow(2) + (size.height / size.width).pow(2))
}

@Suppress("UNCHECKED_CAST")
@Composable
private fun UpdateEffectApi32(
    key: Any?,
    modifier: Modifier = Modifier,
    containerColor: Color,
    effectColor: Color,
    animationSpec: AnimationSpec<Float>
) {
    val scale = remember { Animatable(0f) }
    val updatedEffectColor = remember { Animatable(Color.Transparent) }
    LaunchedEffect(key) {
        if (updatedEffectColor.value != Color.Transparent) {
            updatedEffectColor.animateTo(Color.Transparent)
        }
        launch {
            updatedEffectColor.animateTo(effectColor, animationSpec as AnimationSpec<Color>)
        }
        var launched = false
        scale.animateTo(2f, animationSpec) {
            if (value >= 1f && !launched) {
                launched = true
                launch {
                    updatedEffectColor.animateTo(
                        Color.Transparent,
                        animationSpec as AnimationSpec<Color>
                    )
                }
            }
        }
        scale.snapTo(0f)
    }
    Box(modifier.drawBehind {
        drawRect(containerColor)
        scale(calculateUVRadius(size) * scale.value, pivot = center) {
            drawRect(
                Brush.radialGradient(
                    0f to Color.Transparent,
                    0.5f to updatedEffectColor.value.copy(alpha = 1f - abs(scale.value - 1f)),
                    1f to Color.Transparent,
                )
            )
        }
    })
}

@LightDarkPreview
@Composable
private fun UpdateEffectPreview() {
    WallManPreviewTheme {
        var key by remember { mutableIntStateOf(0) }
        LaunchedEffect(Unit) {
            while (true) {
                key += 1
                delay(1000)
            }
        }
        UpdateEffect(
            key,
            Modifier
                .size(200.dp)
        )
    }
}