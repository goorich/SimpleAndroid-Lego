/*
 * Copyright 2019. techflowing
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
 */

package com.neuqer.android.apt.runtime;

import com.google.auto.service.AutoService;
import com.neuqer.android.annotation.ModuleApplication;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * ModuleApplication 注解处理，示例使用无实际意义
 *
 * @version v1.0.0
 * @since 2019-07-20 16:36
 */
@AutoService(Processor.class)
public class ModuleApplicationProcessor extends AbstractProcessor {

    private static final HashSet<String> set = new HashSet<>();

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(ModuleApplication.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return true;
    }
}
