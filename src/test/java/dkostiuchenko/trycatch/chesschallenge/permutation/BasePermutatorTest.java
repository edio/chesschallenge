package dkostiuchenko.trycatch.chesschallenge.permutation;

import dkostiuchenko.trycatch.chesschallenge.chess.Piece;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Base class to test permutation strategy
 */
public abstract class BasePermutatorTest {

    public static class CountingCollector implements PermutationCollector {
        public long count;

        @Override
        public void collect(Permutation permutation) {
            count++;
        }

        public long getCount() {
            return count;
        }
    }

    public static class UniqueArrayCountingCollector implements PermutationCollector {
        private final Set<Permutation> set = new HashSet<>();

        @Override
        public void collect(Permutation permutation) {
            Permutation p = copy(permutation);
            set.add(p);
        }

        private Permutation copy(Permutation permutation) {
            return new Permutation(Arrays.copyOf(permutation.getElements(), permutation.getElements().length));
        }

        public long getCount() {
            return set.size();
        }
    }

    protected long countPermutations(PermutationStrategy p, Piece[] initialState) {
        final CountingCollector collector = new CountingCollector();
        p.permute(initialState, collector);
        return collector.getCount();
    }

    protected long countUniquePermutations(PermutationStrategy p, Piece[] initialState) {
        UniqueArrayCountingCollector collector = new UniqueArrayCountingCollector();
        p.permute(initialState, collector);
        return collector.getCount();
    }

    /**
     * Counts number of arrangements of specific number of elements from set of specific size
     *
     * @param from total number of elements in set
     * @param of   number of arranged elements
     * @return number of arrangements
     */
    public static long arrangements(int from, int of) {
        int lowerBound = from == of ? 1 : from - of;
        return product(lowerBound, from);
    }

    /**
     * Number of permutations in multiset (set with duplicates)
     *
     * @param setSize               total set sie
     * @param repeatableItemsCounts counts of items that are duplicated
     * @return number of permutations
     */
    public static long multisetPermutations(int setSize, int... repeatableItemsCounts) {
        long result = factorial(setSize);
        for (int repeatableItemsCount : repeatableItemsCounts) {
            result /= factorial(repeatableItemsCount);
        }
        return result;
    }

    public static long factorial(int n) {
        long result = 1;
        if (n != 0) {
            return product(1, n);
        } else {
            return 1;
        }
    }

    private static long product(int lower, int upper) {
        long result = lower;
        for (int i = lower + 1; i <= upper; i++) {
            result *= i;
        }
        return result;
    }


}