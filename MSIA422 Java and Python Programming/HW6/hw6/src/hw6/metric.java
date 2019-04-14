package hw6;


// the metric I chose is Variance of the number of names in each line

public class metric {
    String[] hash;
    Integer size;
    public metric(String[] hash, Integer size) {
        this.hash = hash;
        this.size = size;
    }

    public double getVar () {
        double sum = 0.0;
        for(String s : this.hash) {
        	//count the number of names in each line (-1 because we have row number "x: " at the very beginning)
            sum += s.split(" ").length - 1;
        }
        double mean = sum/this.size;
        double sumSquare = 0.0;
        for(String s :this.hash) {
        	//find sum of (x-x_bar)^2
            sumSquare += (s.split(" ").length - 1 - mean) * (s.split(" ").length - 1 - mean);
        }
        //calculate variance
        return sumSquare / this.size;
    }
}
