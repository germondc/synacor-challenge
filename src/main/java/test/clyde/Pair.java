package test.clyde;

import java.util.Objects;

public class Pair<L extends Comparable<L>,R extends Comparable<R>> implements Comparable<Pair<L,R>> {
    private L left;
    private R right;
    
    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }
    
    public L getLeft() {
        return left;
    }
    
    public R getRight() {
        return right;
    }

    @Override
    public int compareTo(Pair<L, R> o) {
        int leftComp = left.compareTo(o.left);
        if (leftComp != 0)
            return leftComp;
        return right.compareTo(o.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pair<L,R> other = (Pair<L,R>) obj;
        return Objects.equals(left, other.left) && Objects.equals(right, other.right);
    }

    @Override
    public String toString() {
        return "Pair [left=" + left + ", right=" + right + "]";
    }
}
