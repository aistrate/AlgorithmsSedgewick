

// Topological sort: Given a digraph, give a permutation of the vertices such that if v->w for all 
// the edges then in that permutation, w never comes before v.

// Lets say the graph is edges of pre-reqs, then if you take courses in the topological order,
// It will never occur in the list that a course has a pre-req and it is found later in the list.

public class Ex_4_2_10 {

/**
 * Given a DAG, does there exist a topological order that cannot result from applying
 * a DFS-based algorithm, no matter in what order the vertices adjacent to each
 * vertex are chosen? Prove your answer.
 * @author VINCE
 *
 */

//Yes that can be such a case. 
//1->2
//1->3
//3->4
//
//Topological sort order: [1 3 2 4]
//Can never be achieved through DFS.

}
