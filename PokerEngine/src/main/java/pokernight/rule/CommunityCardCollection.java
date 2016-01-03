package pokernight.rule;

import java.awt.geom.Point2D;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommunityCardCollection {
    public final String name; // Required
    public final String[] cardGridLocations; // Required
    private int curIndex = 0;

    CommunityCardCollection(@JsonProperty("collectionName") String name,
                            @JsonProperty("cardGridLocations") String[] cardGridLocations) {
        this.name = name;
        this.cardGridLocations = cardGridLocations;
    }

    public int[] getNextGridLocation() {
        String curPoint = "";
        int[] returnPoint = null;
        if (curIndex < cardGridLocations.length) {
            curPoint = cardGridLocations[curIndex];
            String[] tokens = curPoint.split("-");
            returnPoint = new int[]{Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])};
        }
        curIndex++;
        return returnPoint;
    }

    public boolean hasNext() {
        return curIndex < cardGridLocations.length;
    }

    public void resetNext() {
        curIndex = 0;
    }

    @Override
    public String toString() {
        return "CommunityCardCollection [collectionName=" + name
                + ", cardGridLocations=" + Arrays.toString(cardGridLocations)
                + "]";
    }

}
