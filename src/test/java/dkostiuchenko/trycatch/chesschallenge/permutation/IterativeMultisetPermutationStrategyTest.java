package dkostiuchenko.trycatch.chesschallenge.permutation;

import dkostiuchenko.trycatch.chesschallenge.chess.Piece;
import org.junit.Assert;
import org.junit.Test;

public class IterativeMultisetPermutationStrategyTest extends AbstractMultisetPermutationStrategyTest {
    @Override
    protected PermutationStrategy buildStrategy() {
        return new IterativeMultisetPermutationStrategy();
    }
}