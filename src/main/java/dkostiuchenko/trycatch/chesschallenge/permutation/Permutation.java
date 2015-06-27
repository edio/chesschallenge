package dkostiuchenko.trycatch.chesschallenge.permutation;

import dkostiuchenko.trycatch.chesschallenge.chess.Board;
import dkostiuchenko.trycatch.chesschallenge.chess.Piece;

import java.util.*;

public class Permutation {
    private final Piece[] elements;
    // for each Piece's ordinal holds array of coordinates Piece is placed on
    private final Map<Piece, int[]> index;

    public Permutation(Piece[] elements) {
        this.elements = elements;
        this.index = buildIndex();
    }

    private Map<Piece, int[]> buildIndex() {
        // we don't use list in the end because each permutation would produce one Integer object
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

        final Map<Piece, int[]> arrayBasedIndex = new HashMap<>();

        for (Map.Entry<Piece, List<Integer>> pieceCoordinates : listBasedIndex.entrySet()) {
            final List<Integer> coordinatesList = pieceCoordinates.getValue();
            final int[] coordinates = new int[coordinatesList.size()];
            for (int i = 0; i < coordinatesList.size(); i++) {
                int coordinate = coordinatesList.get(i);
                coordinates[i] = coordinate;
            }
            arrayBasedIndex.put(pieceCoordinates.getKey(), coordinates);
        }

        return arrayBasedIndex;
    }

    /**
     * Move piece shifting all items as necessary
     *
     * @param from
     * @param to
     */
    public void move(int from, int to) {
        if (from == to) {
            return;
        }
        Piece toMove = elements[from];
        if (from > to) {
            System.arraycopy(elements, to, elements, to + 1, from - to);
            for (int[] coordinates : index.values()) {
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
            for (int[] coordinates : index.values()) {
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
    public void swap(int that, int anotherOne) {
        Piece thatPiece = elements[that];
        Piece anotherPiece = elements[anotherOne];

        elements[anotherOne] = thatPiece;
        elements[that] = anotherPiece;

        // update index
        if (thatPiece != Piece.NONE) {
            final int[] coordinates = index.get(thatPiece);
            for (int i = 0; i < coordinates.length; i++) {
                if (coordinates[i] == that) {
                    coordinates[i] = anotherOne;
                }
            }
        }

        if (anotherPiece != Piece.NONE) {
            final int[] coordinates = index.get(anotherPiece);
            for (int i = 0; i < coordinates.length; i++) {
                if (coordinates[i] == anotherOne) {
                    coordinates[i] = that;
                }
            }
        }
    }

    /**
     * Get underlying array of this permutation
     *
     * @return the same array instance used in this {@link Permutation} instance
     */
    public Piece[] getElements() {
        return elements;
    }

    /**
     * Get index of the elements in this permutation.
     * Index holds all not {@link Piece#NONE} pieces and coordinates of those pieces in underlying array
     * ({@link #getElements()}).
     * <p/>
     * Index doesn't hold {@link Piece#NONE} because it would give questionable performance benefit for {@link Board}
     * clients but would slow-down permutations generation for typical scenarios.
     *
     * @return the same index instance, used in this {@link Permutation} instance
     */
    public Map<Piece, int[]> getIndex() {
        return index;
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
}
