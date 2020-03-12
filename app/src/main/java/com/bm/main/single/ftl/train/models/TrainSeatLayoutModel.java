package com.bm.main.single.ftl.train.models;


import androidx.annotation.NonNull;

/**
 * Created by rizabudiprasetya on 8/8/17.
 */

public class TrainSeatLayoutModel extends TrainJSONModel {
    private int row;
    private TrainSeatLayoutItemModel ColA, ColB, ColC, ColD, ColE;

    public void setLayout(@NonNull TrainSeatLayoutItemModel seatModelLayoutItem) {
        row = seatModelLayoutItem.getRow();
        String column = seatModelLayoutItem.getColumn();

        if(column.equals("A"))
            ColA = seatModelLayoutItem;
        else if(column.equals("B"))
            ColB = seatModelLayoutItem;
        else if(column.equals("C"))
            ColC = seatModelLayoutItem;
        else if(column.equals("D"))
            ColD = seatModelLayoutItem;
        else if(column.equals("E"))
            ColE = seatModelLayoutItem;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public TrainSeatLayoutItemModel getColA() {
        return ColA;
    }

    public void setColA(TrainSeatLayoutItemModel colA) {
        ColA = colA;
    }

    public TrainSeatLayoutItemModel getColB() {
        return ColB;
    }

    public void setColB(TrainSeatLayoutItemModel colB) {
        ColB = colB;
    }

    public TrainSeatLayoutItemModel getColC() {
        return ColC;
    }

    public void setColC(TrainSeatLayoutItemModel colC) {
        ColC = colC;
    }

    public TrainSeatLayoutItemModel getColD() {
        return ColD;
    }

    public void setColD(TrainSeatLayoutItemModel colD) {
        ColD = colD;
    }

    public TrainSeatLayoutItemModel getColE() {
        return ColE;
    }

    public void setColE(TrainSeatLayoutItemModel colE) {
        ColE = colE;
    }
}
