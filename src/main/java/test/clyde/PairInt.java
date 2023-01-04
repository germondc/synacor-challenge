package test.clyde;

import java.util.Objects;

public class PairInt implements Comparable<PairInt> {
    private int left;
    private int right;
    
    public PairInt(int left, int right) {
        this.left = left;
        this.right = right;
    }
    
    public int getLeft() {
        return left;
    }
    
    public int getRight() {
        return right;
    }

    @Override
    public int compareTo(PairInt o) {
        int leftComp = left - o.left;
        if (leftComp != 0)
            return leftComp;
        return right - o.right;
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PairInt other = (PairInt) obj;
        return Objects.equals(left, other.left) && Objects.equals(right, other.right);
    }

    @Override
    public String toString() {
        return "Pair [left=" + left + ", right=" + right + "]";
    }
}
