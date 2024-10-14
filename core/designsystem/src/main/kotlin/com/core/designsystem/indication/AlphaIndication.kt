package com.core.designsystem.indication

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.requireGraphicsContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

object AlphaIndication : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode = AlphaIndicationNode(interactionSource)

    override fun equals(other: Any?): Boolean = other === this

    override fun hashCode(): Int = 200
}

private class AlphaIndicationNode(
    private val interactionSource: InteractionSource,
) : Modifier.Node(),
    DrawModifierNode {

    private val animatedAlpha = Animatable(1f)

    private lateinit var graphicsLayer: GraphicsLayer

    private suspend fun animateToPressed() {
        animatedAlpha.animateTo(0.5f)
    }

    private suspend fun animateToResting() {
        animatedAlpha.animateTo(1f)
    }

    override fun onAttach() {
        graphicsLayer = requireGraphicsContext().createGraphicsLayer()
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

    override fun onDetach() {
        super.onDetach()
        requireGraphicsContext().releaseGraphicsLayer(graphicsLayer)
    }

    override fun ContentDrawScope.draw() {
        graphicsLayer.alpha = animatedAlpha.value
        graphicsLayer.record {
            this@draw.drawContent()
        }
        drawLayer(graphicsLayer)
    }
}
