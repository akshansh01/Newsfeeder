   The Linking Open Data (LOD) project is an ongoing effort to construct a global data space, i.e. the Web of Data. One important part of this project is to establish owl:sameAs links among structured data sources. Such links indicate equivalent instances that refer to the same real-world object. The problem of discovering owl:sameAs links between pairwise data sources is called instance matching. Most of the existing approaches addressing this problem rely on the quality of prior schema matching, which is not always good enough in the LOD scenario.

After reading the paper: SLINT: A Schema-Independent Linked Data Interlinking System, link:http://ri-www.nii.ac.jp/SLINT/index.html, I learned that we need an approach to reduce the amount of unnecessary instance matching. Therefore, I can figure out a method firstly to select the candidate instances to do the mathcing step.

Here, I figure out the approach that is :

Step 1:

1.Find the important predicates bsed on coverage(p,D); 

2: Predicate Alignment. From selected predicates of source data and target data, we select the alignments whose confidence is higher than threshold ¦Ä ; 

3: To be finished, in order to do the instance alignment candidate selection.




Besides,based on the paper "A Machine Learning Approach for Instance Matching Based on Similarity Metrics" , I propose a schema-independent instance-pair similarity metric based on several general descriptive features. And transform the instance matching problem to the binary classification problem and solve it by machine learning algorithms.

Step 2:

1. Multidimensional similarity metric for each instance pair.
   
Multidimensional features: so far there are only 3 feature metrics as follows, but the metrics are schema-independent, and later more metric will be added, i.e. values of porperty similarity metric, cosine similarity metirc for texts can be used. 

   Lable-based matching
   Property-based matching
   Number-based matching 

2. Binary classifier [decision tree], each non-leaf node t has a dimension-number k and a threshold,each leaf node has a label of two classes 
[-1 class stands for non-matching instances; 1 class stands for match instances].

3. Using genetic programming to learn the decision tree based on reference links. 


Here I use the 2011 dataset,the anatomy real world case is about matching the Adult Mouse Anatomy (2744 classes) and the NCI Thesaurus (3304 classes) describing the human anatomy. here! 
Mouse-Ontology  <mouse.owl>
Human-Ontology  <human.owl>
Reference alignment
