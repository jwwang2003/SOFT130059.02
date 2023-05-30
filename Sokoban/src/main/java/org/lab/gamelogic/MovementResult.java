package org.lab.gamelogic;

public class MovementResult {
    /*
     * 角色昵称   角色的合法移动次数  错误移动次数      错误输入指令数           角色碰墙的次数   与障碍物重叠的次数      箱子碰墙次数              箱子碰障碍物次数
     * playName  validMoveCount   wrongMoveCount  invalidKeystrokeCount hitWallCount    overlapCount        boxWallCollisionCount   boxBlockadeCollisionCount
     *
     */
    private int validMoveCount, wrongMoveCount,
            invalidKeystrokeCount,
            hitWallCount, overlapCount,
            boxWallCollisionCount, boxBlockadeCollisionCount;

    public MovementResult() {
        validMoveCount = 0;
        wrongMoveCount = 0;
        invalidKeystrokeCount = 0;
        overlapCount = 0;
        hitWallCount = 0;
        boxWallCollisionCount = 0;
        boxBlockadeCollisionCount = 0;
    }

    public void invokedValidMove() { ++validMoveCount; }
    public void invokedWrongMove() { ++wrongMoveCount; }
    public void invokedInvalidKeystroke() { ++invalidKeystrokeCount; }
    public void invokedOverlapMove() { ++overlapCount; }
    public void invokedHitWallMove() { ++hitWallCount; }
    public void invokedBoxBlockadeCollision() { ++boxBlockadeCollisionCount; }
    public void invokedBoxWallCollision() { ++boxWallCollisionCount; }

    public int getValidMoves() { return validMoveCount; }
    public int getWrongMoves() { return wrongMoveCount; }
    public int getInvalidKeystrokes() { return invalidKeystrokeCount; }
    public int getOverlapMoves() { return overlapCount; }
    public int getHitWallMoves() { return hitWallCount; }
    public int getBoxBlockadeCollisions() { return boxBlockadeCollisionCount; }
    public int getBoxWallCollisions() { return boxWallCollisionCount; }

    public void sum(MovementResult res) {
        validMoveCount += res.getValidMoves();
        wrongMoveCount += res.getWrongMoves();
        invalidKeystrokeCount += res.getInvalidKeystrokes();
        overlapCount += res.getOverlapMoves();
        hitWallCount += res.getHitWallMoves();
        boxWallCollisionCount += res.getBoxWallCollisions();
        boxBlockadeCollisionCount += res.getBoxBlockadeCollisions();
    }

    @Override
    public String toString() {
        return "[" + validMoveCount + " " + wrongMoveCount + " " + invalidKeystrokeCount + " " +
                hitWallCount + " " + overlapCount + " " +
                boxWallCollisionCount+ " " + boxBlockadeCollisionCount + "]";
    }
}
