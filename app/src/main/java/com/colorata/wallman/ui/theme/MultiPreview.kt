package com.colorata.wallman.ui.theme

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview


@Preview(
    name = "Light Compact",
    device = "spec:shape=Normal,width=411,height=891,unit=dp,dpi=420",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true,
    group = "Compact"
)
@Preview(
    name = "Dark Compact",
    device = "spec:shape=Normal,width=411,height=891,unit=dp,dpi=420",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true,
    group = "Compact"
)
@Preview(
    name = "Light Medium",
    device = "spec:shape=Normal,width=674,height=841,unit=dp,dpi=480",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true,
    group = "Medium"
)
@Preview(
    name = "Light Expanded",
    device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    showBackground = true,
    group = "Expanded"
)
annotation class MultiPreview
