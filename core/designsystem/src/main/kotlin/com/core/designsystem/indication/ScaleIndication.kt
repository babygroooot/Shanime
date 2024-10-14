package com.core.designsystem.indication

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

object ScaleIndication : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode = ScaleIndicationNode(interactionSource)

    override fun equals(other: Any?): Boolean = other === this

    override fun hashCode(): Int = 100
}

private class ScaleIndicationNode(
    private val interactionSource: InteractionSource,
) : Modifier.Node(),
    DrawModifierNode {

    val animatedScalePercent = Animatable(initialValue = 1f)

    private suspend fun animateToPressed() {
        animatedScalePercent.animateTo(
            targetValue = 0.97f,
            animationSpec = tween(),
        )
    }

    private suspend fun animateToResting() {
        animatedScalePercent.animateTo(
            targetValue = 1f,
            animationSpec = tween(),
        )
    }

    override fun onAttach() {
        coroutineScope.launch {
            interactionSource.interactions.collectLatest { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> {
                        animateToPressed()
                    }

                    is PressInteraction.Cancel -> {
                        animateToResting()
                    }

                    is PressInteraction.Release -> {
                        animateToResting()
                    }
                }
            }
        }
    }

    override fun ContentDrawScope.draw() {
        scale(
            scale = animatedScalePercent.value,
        ) {
            this@draw.drawContent()
        }
    }
}
