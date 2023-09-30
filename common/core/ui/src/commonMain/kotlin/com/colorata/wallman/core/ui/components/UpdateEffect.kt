package com.colorata.wallman.core.ui.components

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
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
import com.colorata.wallman.core.data.animation
import com.colorata.wallman.core.data.memoize
import com.colorata.wallman.core.ui.LightDarkPreview
import com.colorata.wallman.core.ui.theme.WallManPreviewTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.Language
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

data class UpdateEffectState(
    val radius: Animatable<Float, AnimationVector1D> = Animatable(0f),
    val color: Animatable<Color, AnimationVector4D> = Animatable(Color.Transparent)
)

@Composable
fun UpdateEffect(
    key: Any?,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    effectColor: Color = MaterialTheme.colorScheme.primary,
    animationSpec: AnimationSpec<Float> = MaterialTheme.animation.emphasized(MaterialTheme.animation.durationSpec.extraLong4 * 3)
) {
    val states = remember { mutableStateListOf<UpdateEffectState>() }
    val scope = rememberCoroutineScope()
    val updatedEffectColor by rememberUpdatedState(effectColor)
    LaunchedEffect(key) {
        scope.launch {
            val state = UpdateEffectState()
            states.add(state)
            scope.launch {
                state.color.animateTo(updatedEffectColor)
            }
            state.radius.animateTo(1f, animationSpec)
            // Removing is thread safe because there is always single instance of state
            states.remove(state)
        }
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        UpdateEffectApi33(
            states = states.toImmutableList(),
            containerColor = containerColor,
            modifier = modifier,
        )
    } else {
        UpdateEffectApi32(
            states = states.toImmutableList(),
            containerColor = containerColor,
            modifier = modifier
        )
    }
}

@RequiresApi(33)
@Composable
private fun UpdateEffectApi33(
    states: ImmutableList<UpdateEffectState>,
    containerColor: Color,
    modifier: Modifier = Modifier,
) {
    val statesWithShaders = remember(states) {
        states.map { it to RuntimeShader(SHADER) }
    }
    Box(
        modifier
            .fillMaxSize()
            .forEach(statesWithShaders) { (state, shader) ->
                graphicsLayer {
                    clip = true
                    with(shader) {
                        setFloatUniform("iTime", state.radius.value * memoizedCalculateRadius(size))
                        setColorUniform("iSparkleColor", state.color.value.toArgb())
                        setFloatUniform(
                            "iResolution",
                            size.width,
                            size.height
                        )
                    }
                    renderEffect =
                        RenderEffect
                            .createRuntimeShaderEffect(shader, "in_shader")
                            .asComposeRenderEffect()
                }
            }
            .background(containerColor))
}

fun <T> Modifier.forEach(list: List<T>, modifier: Modifier.(T) -> Modifier): Modifier =
    if (list.isEmpty()) this else then(this.modifier(list.last())) then forEach(
        list.dropLast(1),
        modifier
    )

private val memoizedCalculateRadius = memoize<Size, Float> { size ->
    sqrt((size.width / size.height).pow(2) + (size.height / size.width).pow(2))
}

@Suppress("UNCHECKED_CAST")
@Composable
private fun UpdateEffectApi32(
    states: ImmutableList<UpdateEffectState>,
    modifier: Modifier = Modifier,
    containerColor: Color,
) {
    Box(modifier.drawBehind {
        drawRect(containerColor)
        states.forEach { state ->
            val radius = state.radius.value * memoizedCalculateRadius(size)
            scale(radius * 2f, pivot = center) {
                drawRect(
                    Brush.radialGradient(
                        0f to Color.Transparent,
                        0.5f to state.color.value.copy(
                            alpha = (1f - radius).coerceAtLeast(
                                0f
                            )
                        ),
                        1f to Color.Transparent,
                    )
                )
            }
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
                delay(5000)
            }
        }
        UpdateEffect(
            key,
            Modifier
                .size(200.dp)
        )
    }
}