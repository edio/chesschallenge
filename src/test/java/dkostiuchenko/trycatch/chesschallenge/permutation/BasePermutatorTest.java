package dkostiuchenko.trycatch.chesschallenge.permutation;

import dkostiuchenko.trycatch.chesschallenge.chess.Piece;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Base class to test permutation strategy
 */
public abstract class BasePermutatorTest {

    public static class CountingCollector implements Permutator.PermutationCollector {
        public long count;

        @Override
        public void collect(Piece[] permutation) {
            count++;
        }

        public long getCount() {
            return count;
        }
    }

    public static class UniqueArrayCountingCollector implements Permutator.PermutationCollector {
        private final Set<HashableArrayWrapper> set = new HashSet<>();

        @Override
        public void collect(Piece[] permutation) {
            set.add(new HashableArrayWrapper(permutation));
        }

        public long getCount() {
            return set.size();
        }
    }

    private static class HashableArrayWrapper {
        private final Object[] array;

        private HashableArrayWrapper(Object[] array) {
            this.array = Arrays.copyOf(array, array.length);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            HashableArrayWrapper that = (HashableArrayWrapper) o;
            return Arrays.equals(array, that.array);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(array);
        }
    }

    protected long countPermutations(Permutator p, Piece[] initialState) {
        final CountingCollector collector = new CountingCollector();
        p.permute(initialState, collector);
        return collector.getCount();
    }

    protected long countUniquePermutations(Permutator p, Piece[] initialState) {
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