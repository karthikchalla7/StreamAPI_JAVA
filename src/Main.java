import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import  java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**

 *Streams in java

 - we can consider stream as a pipeline, through which our colleciton elements passes through
 - while elements passes through pipelines, it perform various operations like sorting,filtering etc
 - useful when deals with bulk processing.(can do parallel processing)

 Collection
 1.Create Stream
 streams are created from the data source like collection of array,list etc

 2.Intermediate operations
 These operations makes the data look more valuable
 These operations transform the stream into another streams and many opertaions we can do
 These are lazy in nature means these operations get executed only when terminal operation is invoked
 we have methods like .sort() .filter() .map() .distinct() etc

 3.Terminal operations
 - These operations triggers the processing of the stream.
 - only one terminal operation can be used coz once the terminal executed it closes the stream
 - And produce the ouput means after terminal operation used,no more operation we can perform
 we have methods like - .collect() .reduce() .count() etc

 */
public class Main {
    public static void main(String[] args) {

        //using stream

        List<Integer> list = Arrays.asList(100, 200, 300);

        long res = list.stream()
                .filter(sal -> sal>20)
                .count();
        System.out.println("count of >50 "+res);
        
        //Different ways to create a streams
        /**

         - from collections
         List<Integer> list = Arrays.asList(1000,....);
         Stream<Integer> stream = list.stream();

         - from array
         Integer[] arr = {100,...};
         Stream<Integer> stream = Arrays.stream(arr);

         -from static methods
         Stream<Integer> stream = Stream.of(100,200,300);

         - from stream builder:
         Stream.Builder<Integer> streamBuilder = stream.builder();
         streambuilder.add(100).add(200)...;

         Stream<Integer> bulilder = streamBuilder.build();

         -from stream iterate
         Stream<Integer> streamfromiterate = Stream.iterate(100,(n -> n+100)).limit(5);

         why we call intermediate operation "lazy"?
         Streams will give output only when terminal operators are used.


         */

        /**
         * Different intermediate operations:

         we can chain multiple intermediate operation together to perfrom more complex procesing befoer applying terminal opeation to produce the result
         */

        //filter(Predicate <T> predicate)
        Stream<String> name = Stream.of("Karthik","arman","apple","zebra");
        //Stream<String> filtered = name.filter( names -> names.length() <=6);
        //collect the stream in a list to get the output we use terminal operator
        //.collect terminal operator
        //List<String> filterlist = filtered.collect(Collectors.toList());
        //System.out.println(filterlist);

        //map used to transform each element
        Stream<String> filteredmap = name.map(names -> names.toLowerCase());
        //we have to use terminal operators to get the output from the stream
        List<String> filtermap = filteredmap.collect(Collectors.toList());
        System.out.println(filtermap);

        //flatmap - used to iterate over each element of the complex collection and helps to flatten it
        List<List<String>> list1 = Arrays.asList(
                Arrays.asList("I","Love","Java"),
                Arrays.asList("Concepts","are","fun"),
                Arrays.asList("Its","very","easy")
        );

        Stream<String> wordstream = list1.stream()
                .flatMap((List<String> word) -> word.stream());
        Stream<String> wordstream1 = list1.stream()
                .flatMap((List<String> word) -> word.stream().map(value -> value.toLowerCase()));

        List<String> ouptut = wordstream.collect(Collectors.toList());
        System.out.println(ouptut);

        //distint - removes duplicates from the stream;
        Integer[] arr = {1,2,4,1,5,3,2};
        Stream<Integer> arrstream = Arrays.stream(arr).distinct();
        List<Integer> list2 = arrstream.collect(Collectors.toList());
        System.out.println(list2);

        //sorted sorts the elements
        Integer[] arr1 = {1,2,4,1,5,3,2};
        Stream<Integer> arr1stream = Arrays.stream(arr1).sorted();
        List<Integer> list3 = arr1stream.collect(Collectors.toList());
        System.out.println(list3);

        Stream<Integer> arr2stream = Arrays.stream(arr1).sorted((val1,val2) -> val2-val1);
        List<Integer> list4 = arr2stream.collect(Collectors.toList());
        System.out.println(list4);

        //peak - helps you to see the intermediate result of stream which is getting processed
        //limit - truncate the stream to have no longer than given maxSize
        //skip - skip the first n elements of the stream
        //mapToInt - helps to work with primitve "int" data types
        //mapToLong,Double
//        int[] nums = {1,2,4,5};
//        IntStream numberstream = Arrays.stream(nums);
//        numberstream.filter(val -> val>2);
//        int[] filteredarray = numberstream.toArray();
//        System.out.println(filteredarray);

        //sqeuence of stream operations

        List<Integer> numbers = Arrays.asList(2,1,4,7,5);
        Stream<Integer> numberstream = numbers.stream()
                .filter(val -> val>=3)
                .peek(val -> System.out.println("after fileter: "+val))
                .map(val -> val*-1)
                .peek(val -> System.out.println("after negating: "+val))
                .sorted()
                .peek(val -> System.out.println("after sorted: "+val));
        List<Integer> filterdnum = numberstream.collect(Collectors.toList());
        System.out.println(filterdnum);

        //so the series of stream goes through one value from each filter then prints and goes to next value in the array


        /**
         * Different terminal operations - terminal operators are the ones that produce the result.
         * It triggers the processing of the stream
         */

        //forEach - pefrom action on each element of the stream don't return any value

        List<Integer> num = Arrays.asList(2,4,5,6,8,6);
        num.stream()
                .filter(val -> val>3)
                .forEach(val -> System.out.println(val));

        //toArray() - returns the stream elements into array
        Object[] filter = num.stream()
                .filter(val -> val>3)
                .toArray((int size) -> new Integer[size]);

        //reduce - does reduction on the elements of the stream.
        //perform associative aggregation function like a,b,c a+b then (a+b)+c

        Optional<Integer> reduced = num.stream()
                .reduce((val1,val2) -> val1+val2);
        System.out.println(reduced.get());

        //collect - can be used to collect the elements of the stream into an list
        //min,max use compartor

        Optional<Integer> minmax = num.stream()
                .min((val1,val2)->val1-val2);
        System.out.println(minmax.get());

        //count - returns the numbers of elements in the stream

        //any match - check if any vale in the stream match the given predicate and return the boolean

        boolean greater = num.stream()
                .anyMatch(val->val>3);
        System.out.println(greater);
        //allmatch and nonemathc
        //findFirst() - finds the first element of the stream
        //findAny() - gives random value from the stream

        /**
         * How many times we can use a single stream?
         one terminal operation is used on a stream.
         it is closed and can not be used again for another terminal operation

         parallel stream
         - helps to perfrom operation on stream concurrently taking advatage of multi core CPU.
         - ParallelStream() method is used instead of regular stream method
         - internally it does:
         - task splitting: it uses spliterator function to split the data into multiple chunks
         - task submission and parallel processing: uses Fork-Join pool technique
         */

        //parallel stream
        //sequential processing
        long seqtime = System.currentTimeMillis();
        num.stream()
                .map(val -> val*val)
                .forEach(val -> System.out.println(val));
        System.out.println("seq processing time: "+ (System.currentTimeMillis()-seqtime));

        //parallel processing
        long partime = System.currentTimeMillis();
        num.parallelStream()
                .map(val -> val*val)
                .forEach(val -> System.out.println(val));
        System.out.println("parallel processing time: "+ (System.currentTimeMillis()-partime));


    }
}