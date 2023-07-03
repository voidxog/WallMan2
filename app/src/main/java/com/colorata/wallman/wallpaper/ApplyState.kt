package com.colorata.wallman.wallpaper

import com.colorata.wallman.arch.Polyglot
import com.colorata.wallman.arch.Strings

enum class ApplyState(val message: Polyglot) {
    NotApplied(Strings.ApplyStates.notApplied),
    SureMessage(Strings.ApplyStates.sure),
    Applying(Strings.ApplyStates.applying),
    Applied(Strings.ApplyStates.applied),
    ErrorApplying(Strings.ApplyStates.error)
}