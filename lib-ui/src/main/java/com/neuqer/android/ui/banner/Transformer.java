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
 *  Origin: https://github.com/youth5201314/banner
 */

package com.neuqer.android.ui.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.neuqer.android.ui.banner.transformer.AccordionTransformer;
import com.neuqer.android.ui.banner.transformer.BackgroundToForegroundTransformer;
import com.neuqer.android.ui.banner.transformer.CubeInTransformer;
import com.neuqer.android.ui.banner.transformer.CubeOutTransformer;
import com.neuqer.android.ui.banner.transformer.DefaultTransformer;
import com.neuqer.android.ui.banner.transformer.DepthPageTransformer;
import com.neuqer.android.ui.banner.transformer.FlipHorizontalTransformer;
import com.neuqer.android.ui.banner.transformer.FlipVerticalTransformer;
import com.neuqer.android.ui.banner.transformer.ForegroundToBackgroundTransformer;
import com.neuqer.android.ui.banner.transformer.RotateDownTransformer;
import com.neuqer.android.ui.banner.transformer.RotateUpTransformer;
import com.neuqer.android.ui.banner.transformer.ScaleInOutTransformer;
import com.neuqer.android.ui.banner.transformer.StackTransformer;
import com.neuqer.android.ui.banner.transformer.TabletTransformer;
import com.neuqer.android.ui.banner.transformer.ZoomInTransformer;
import com.neuqer.android.ui.banner.transformer.ZoomOutSlideTransformer;
import com.neuqer.android.ui.banner.transformer.ZoomOutTranformer;


public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
