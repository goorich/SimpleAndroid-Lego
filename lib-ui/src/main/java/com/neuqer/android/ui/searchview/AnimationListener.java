/*
 * Copyright 2018. techflowing
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * There are modifications to the origin source code.
 * Origin: https://github.com/lapism/SearchBar-SearchView
 */

package com.neuqer.android.ui.searchview;

import android.view.View;

/**
 * 动画状态回调
 *
 * @author techflowing
 * @since 2018/10/19 10:51 PM
 */
public interface AnimationListener {
    /**
     * @return true to override parent. Else execute Parent method
     */
    boolean onAnimationStart(View view);

    boolean onAnimationEnd(View view);

    boolean onAnimationCancel(View view);
}