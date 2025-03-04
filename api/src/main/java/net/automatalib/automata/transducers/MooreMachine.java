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
package net.automatalib.automata.transducers;

import java.util.Collection;

import net.automatalib.automata.UniversalDeterministicAutomaton;
import net.automatalib.automata.graphs.TransitionEdge;
import net.automatalib.automata.graphs.TransitionEdge.Property;
import net.automatalib.automata.graphs.UniversalAutomatonGraphView;
import net.automatalib.automata.visualization.MooreVisualizationHelper;
import net.automatalib.graphs.UniversalGraph;
import net.automatalib.ts.output.MooreTransitionSystem;
import net.automatalib.visualization.VisualizationHelper;

public interface MooreMachine<S, I, T, O> extends UniversalDeterministicAutomaton<S, I, T, O, Void>,
                                                  StateOutputAutomaton<S, I, T, O>,
                                                  MooreTransitionSystem<S, I, T, O> {

    @Override
    default UniversalGraph<S, TransitionEdge<I, T>, O, Property<I, Void>> transitionGraphView(Collection<? extends I> inputs) {
        return new MooreGraphView<>(this, inputs);
    }

    class MooreGraphView<S, I, T, O, A extends MooreMachine<S, I, T, O>>
            extends UniversalAutomatonGraphView<S, I, T, O, Void, A> {

        public MooreGraphView(A automaton, Collection<? extends I> inputs) {
            super(automaton, inputs);
        }

        @Override
        public VisualizationHelper<S, TransitionEdge<I, T>> getVisualizationHelper() {
            return new MooreVisualizationHelper<>(automaton);
        }
    }
}
