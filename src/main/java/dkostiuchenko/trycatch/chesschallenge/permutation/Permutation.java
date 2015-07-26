package dkostiuchenko.trycatch.chesschallenge.permutation;

import dkostiuchenko.trycatch.chesschallenge.chess.Piece;

import java.util.*;

/**
 * Provides high-level operations over collections of elements.
 * <p/>
 * This implementation also maintains search index to efficiently search for elements in this permutation.
 */
public class Permutation {
    private final Piece[] elements;
    // for each piece type holds array of indices, where pieces of that type are placed
    private final Map<Piece, int[]> searchIndex = new HashMap<>();

    /**
     * Construct permutation from array of elements
     */
    public Permutation(Piece[] elements) {
        this.elements = Arrays.copyOf(elements, elements.length);
        updateSearchIndex();
    }

    private void updateSearchIndex() {
        // working with collections is much easier but produces much more garbage and reduces performance by up to 20%
        // we use collections on initial index creation only
        Map<Piece, List<Integer>> listBasedIndex = new HashMap<>();
        for (int i = 0; i < elements.length; i++) {
            Piece element = elements[i];
            if (element != Piece.NONE) {
                if (!listBasedIndex.containsKey(element)) {
                    List<Integer> coordinates = new ArrayList<>();
                    coordinates.add(i);
                    listBasedIndex.put(element, coordinates);
                } else {
                    listBasedIndex.get(element).add(i);
                }
            }
        }

        this.searchIndex.clear();
        for (Map.Entry<Piece, List<Integer>> pieceCoordinates : listBasedIndex.entrySet()) {
            final List<Integer> coordinatesList = pieceCoordinates.getValue();
            final int[] coordinates = new int[coordinatesList.size()];
            for (int i = 0; i < coordinatesList.size(); i++) {
                int coordinate = coordinatesList.get(i);
                coordinates[i] = coordinate;
            }
            this.searchIndex.put(pieceCoordinates.getKey(), coordinates);
        }
    }

    /**
     * Move piece shifting all items as necessary
     */
    public void move(int from, int to) {
        if (from == to) {
            return;
        }
        Piece toMove = elements[from];
        if (from > to) {
            System.arraycopy(elements, to, elements, to + 1, from - to);

            for (int[] coordinates : searchIndex.values()) {
                for (int i = 0; i < coordinates.length; i++) {
                    int c = coordinates[i];
                    if (c < from & c >= to) {
                        coordinates[i]++;
                    } else if (c == from) {
                        coordinates[i] = to;
                    }
                }
            }
        } else {
            System.arraycopy(elements, from + 1, elements, from, to - from);

            for (int[] coordinates : searchIndex.values()) {
                for (int i = 0; i < coordinates.length; i++) {
                    int c = coordinates[i];
                    if (c > from & c <= to) {
                        coordinates[i]--;
                    } else if (c == from) {
                        coordinates[i] = to;
                    }
                }
            }
        }
        elements[to] = toMove;
    }

    /**
     * Swap 2 pieces
     */
    public void swap(int that, int theOther) {
        Piece thatPiece = elements[that];
        Piece theOtherPiece = elements[theOther];

        if (thatPiece == theOtherPiece) {
            return;
        }

        elements[theOther] = thatPiece;
        elements[that] = theOtherPiece;

        if (thatPiece != Piece.NONE) {
            final int[] coordinates = searchIndex.get(thatPiece);
            for (int i = 0; i < coordinates.length; i++) {
                if (coordinates[i] == that) {
                    coordinates[i] = theOther;
                }
            }
        }

        if (theOtherPiece != Piece.NONE) {
            final int[] coordinates = searchIndex.get(theOtherPiece);
            for (int i = 0; i < coordinates.length; i++) {
                if (coordinates[i] == theOther) {
                    coordinates[i] = that;
                }
            }
        }
    }

    /**
     * Compare 2 elements of permutation
     *
     * @return {@code >0} if {@code that} is larger, than {@code theOther}, {@code 0} if elements are equal, {@code
     * <0} otherwise
     */
    public int compare(int that, int theOther) {
        return elements[that].ordinal() - elements[theOther].ordinal();
    }

    /**
     * Number of elements in permutation
     */
    public int length() {
        return elements.length;
    }

    /**
     * Get piece addressed by index in this permutation
     *
     * @throws IllegalArgumentException if index is out of range
     */
    public Piece get(int index) {
        try {
            return elements[index];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Index " + index + " is out of range", e);
        }
    }

    /**
     * Set new piece to this permutation.
     * <p/>
     * Causes complete index rebuilt, thus is an expensive operation.
     *
     * @param piece piece to set
     * @param index place in permutation
     * @throws IllegalArgumentException if index is out of range
     */
    public void set(Piece piece, int index) {
        try {
            elements[index] = piece;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Index " + index + " is out of range", e);
        }
        updateSearchIndex();
    }

    /**
     * Find element in this permutation starting from specified index
     *
     * @param element    element to find
     * @param startIndex index to start search from
     * @return index of found element
     * @throws IllegalArgumentException if there's no requested element after the specified index
     */
    public int indexOf(Piece element, int startIndex) {
        for (int i = startIndex; i < elements.length; i++) {
            if (element == elements[i]) {
                return i;
            }
        }
        throw new IllegalArgumentException("No such element " + element + " after " + startIndex);
    }

    /**
     * Find piece occurrences in this permutation.
     * <p/>
     * Method won't work for {@link Piece#NONE}, returning {@code null}
     *
     * @param piece piece to find
     * @return indices of the piece in this permutation, {@code null} if piece is not present on board
     */
    public int[] indices(Piece piece) {
        // NONEs aren't required for this problem and are not indexed for performance reason
        // For api completness it is desirable to have those, but implementation effort is not justified
        return searchIndex.get(piece);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permutation that = (Permutation) o;
        return Arrays.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    // visible for testing
    Piece[] getElements() {
        return elements;
    }

    /**
     * Perform deep copy of a permutation
     */
    public static Permutation copyOf(Permutation toCopy) {
        return new Permutation(toCopy.elements);
    }

}
