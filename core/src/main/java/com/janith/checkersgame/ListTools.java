package com.janith.checkersgame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class ListTools {
    public boolean findPositionCheck2D(Vector2 target,List<Vector2> positions) {
        for (Vector2 vec : positions) {
            if (vec.x == target.x && vec.y == target.y) {
                return true; // Target Vector2 values found
            }
        }

        return false; // Target Vector2 values not found
    }
    public boolean findPositionCheck3D(Vector2 target,List<List<Vector2>> positions) {
        for (List<Vector2> row : positions) {
            for (Vector2 vec : row) {
                if (vec.x == target.x && vec.y == target.y) {
                    return true; // Target Vector2 values found
                }
            }
        }
        return false; // Target Vector2 values not found
    }




    public int findPosition2D(Vector2 target,List<Vector2> positions) {
        int positionIndex = -1;
        for (int count = 0; count < positions.size(); count++) {
            if (positions.get(count).x == target.x && positions.get(count).y == target.y) {
                positionIndex = count;
                break;
            }
        }
        return positionIndex;
    }

    public int[] findPosition3D(Vector2 target,List<List<Vector2>> positions) {
        for (int row = 0; row < positions.size(); row++) {
            List<Vector2> rowList = positions.get(row);
            for (int col = 0; col < rowList.size(); col++) {
                Vector2 vec = rowList.get(col);
                if (vec.x == target.x && vec.y == target.y) {
                    return new int[]{row, col}; // Return the position as [row, col]
                }
            }
        }
        return null; // Target Vector2 values not found
    }

    public void moveObjectInListV(List<Vector2> list, int currentIndex, int targetIndex) {
        // Validate indices
        if (currentIndex < 0 || currentIndex >= list.size() || targetIndex < 0 || targetIndex >= list.size()) {
            throw new IndexOutOfBoundsException("Invalid index for list operation.");
        }

        // Remove the object from its current position
        Vector2 objectToMove = list.remove(currentIndex);

        // If the target index is after the current index, adjust the target index

        // Insert the object at the target position
        list.add(targetIndex, objectToMove);
    }

    public void moveObjectInListS(List<Sprite> list, int currentIndex, int targetIndex) {
        // Validate indices
        if (currentIndex < 0 || currentIndex >= list.size() || targetIndex < 0 || targetIndex >= list.size()) {
            throw new IndexOutOfBoundsException("Invalid index for list operation.");
        }

        // Remove the object from its current position
        Sprite objectToMove = list.remove(currentIndex);

        // If the target index is after the current index, adjust the target index


        // Insert the object at the target position
        list.add(targetIndex, objectToMove);
    }



}

