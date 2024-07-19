package com.example.todo.presentation.uicomponents.divkit

import android.util.Log
import com.yandex.div.core.DivActionHandler
import com.yandex.div.core.DivViewFacade
import com.yandex.div.json.expressions.ExpressionResolver
import com.yandex.div2.DivAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationDivActionHandler(): DivActionHandler() {
    private val _isClicked: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isClicked = _isClicked.asStateFlow()
    override fun handleAction(
        action: DivAction,
        view: DivViewFacade,
        resolver: ExpressionResolver
    ): Boolean {
        if(super.handleAction(action, view, resolver))
            return true

        val uri = action.url?.evaluate(view.expressionResolver) ?: return false
        if (uri.authority != "navigate" || uri.scheme != "div-action") return false
        _isClicked.value = true
        return true
    }
}
