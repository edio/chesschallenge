package dkostiuchenko.trycatch.chessproblem.permutation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Base class to test permutation strategy
 */
public abstract class BasePermutatorTest<P> {

    public static class CountingCollector<P> implements Permutator.PermutationCollector<P> {
        public long count;

        @Override
        public void collect(P permutation) {
            count++;
        }

        public long getCount() {
            return count;
        }
    }

    public static class UniqueCountingCollector<P> implements Permutator.PermutationCollector<P> {
        private final Set<P> set = new HashSet<>();

        @Override
        public void collect(P permutation) {
            set.add(permutation);
        }

        public long getCount() {
            return set.size();
        }
    }

    public static class UniqueArrayCountingCollector<P> implements Permutator.PermutationCollector<P> {
        private final Set<HashableArrayWrapper> set = new HashSet<>();

        @Override
        public void collect(P permutation) {
            set.add(new HashableArrayWrapper((Object[]) permutation));
        }

        public long getCount() {
            return set.size();
        }
    }

    private static class HashableArrayWrapper {
        private final Object[] array;

        private HashableArrayWrapper(Object[] array) {
            this.array = array;
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

    protected long countPermutations(Permutator<P> p, P initialState) {
        final CountingCollector<P> collector = new CountingCollector<>();
        p.permute(initialState, collector);
        return collector.getCount();
    }

    protected long countUniquePermutations(Permutator<P> p, P initialState) {
        if (initialState.getClass().isArray()) {
            UniqueArrayCountingCollector<P> collector = new UniqueArrayCountingCollector<>();
            p.permute(initialState, collector);
            return collector.getCount();
        } else {
            UniqueCountingCollector<P> collector = new UniqueCountingCollector<>();
            p.permute(initialState, collector);
            return collector.getCount();
        }
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
            result /= repeatableItemsCount;
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