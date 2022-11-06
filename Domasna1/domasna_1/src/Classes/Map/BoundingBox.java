package Classes.Map;

public class BoundingBox {
    private double left;
    private double bottom;
    private double right;
    private double top;

    public BoundingBox(double left, double bottom, double right, double top) {
        this.left = left;
        this.bottom = bottom;
        this.right = right;
        this.top = top;
    }

    public double getLeft() {
        return left;
    }

    public double getBottom() {
        return bottom;
    }

    public double getRight() {
        return right;
    }

    public double getTop() {
        return top;
    }
}
