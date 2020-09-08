package com.example.wp.utils

import com.kennyc.view.MultiStateView

fun MultiStateView.showContentView(){
    this.viewState = MultiStateView.ViewState.CONTENT
}

fun MultiStateView.showEmptyView(){
    this.viewState = MultiStateView.ViewState.EMPTY
}

fun MultiStateView.showLoadingView(){
    this.viewState = MultiStateView.ViewState.LOADING
}

fun MultiStateView.showErrorView(){
    this.viewState = MultiStateView.ViewState.ERROR
}