/* Copyright (C) 2013-2022 TU Dortmund
 * This file is part of AutomataLib, http://www.automatalib.net/.
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
package net.automatalib.util.automata.ads;

import java.util.Collections;
import java.util.Set;

import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Utility class used by the algorithm of {@link LeeYannakakis}.
 *
 * @param <S>
 *         (hypothesis) state type
 * @param <I>
 *         input alphabet type
 * @param <O>
 *         output alphabet type
 *
 * @author frohme
 */
class SplitTreeResult<S, I, O> {

    private final @Nullable SplitTree<S, I, O> delegate;
    private final Set<S> indistinguishableStates;

    SplitTreeResult(final SplitTree<S, I, O> result) {
        this.delegate = result;
        this.indistinguishableStates = Collections.emptySet();
    }

    SplitTreeResult(final Set<S> indistinguishableStates) {
        this.delegate = null;
        this.indistinguishableStates = indistinguishableStates;
    }

    @EnsuresNonNullIf(expression = "this.delegate", result = true)
    public boolean isPresent() {
        return this.delegate != null;
    }

    public @Nullable SplitTree<S, I, O> get() {
        return this.delegate;
    }

    public Set<S> getIndistinguishableStates() {
        return this.indistinguishableStates;
    }

}
