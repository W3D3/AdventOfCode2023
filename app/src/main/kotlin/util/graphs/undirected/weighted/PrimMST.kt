/*
 * Copyright (c) 2017 Kotlin Algorithm Club
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package util.graphs.undirected.weighted

import util.datastructures.IndexedPriorityQueue
import util.datastructures.Queue

class PrimMST(graph: UndirectedWeightedGraph) : MST {
    private var weight: Double = 0.0
    private var edges: Queue<UndirectedWeightedGraph.Edge> = Queue()

    /**
     * distTo[v] = distance  of shortest s->v path
     */
    private val distTo: DoubleArray = DoubleArray(graph.V) { Double.POSITIVE_INFINITY }

    /**
     * edgeTo[v] = last edge on shortest s->v path
     */
    private val edgeTo: Array<UndirectedWeightedGraph.Edge?> = arrayOfNulls(graph.V)

    /**
     * priority queue of vertices
     */
    private val pq: IndexedPriorityQueue<Double> = IndexedPriorityQueue(graph.V)

    private val visited = Array(graph.V) { false }

    init {
        for (s in graph.vertices()) {
            if (!visited[s]) {
                distTo[s] = 0.0
                pq.insert(s, 0.0)
                while (!pq.isEmpty()) {
                    val v = pq.poll().first
                    visited[v] = true
                    for (e in graph.adjacentEdges(v)) {
                        scan(e, v)
                    }
                }
            }
        }

        for (v in edgeTo.indices) {
            val e = edgeTo[v]
            if (e != null) {
                edges.add(e)
                weight += e.weight
            }
        }
    }

    private fun scan(e: UndirectedWeightedGraph.Edge, v: Int) {
        val w = e.other(v)
        if (!visited[w]) { // v-w is obsolete edge
            if (e.weight < distTo[w]) {
                distTo[w] = e.weight
                edgeTo[w] = e
                if (pq.contains(w)) {
                    pq.decreaseKey(w, distTo[w])
                } else {
                    pq.insert(w, distTo[w])
                }
            }
        }
    }

    override fun edges(): Iterable<UndirectedWeightedGraph.Edge> {
        return edges
    }

    override fun weight(): Double {
        return weight
    }
}