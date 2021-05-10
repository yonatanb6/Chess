import java.util.ArrayList;

interface PieceInterface {
  
    // draw the piece
    void draw(int[] position, String filename);

    // move the peice to target square
    void move(Board b, Square target, int[] position, String imagepath);

    // find valid move locations
    ArrayList<int[]> findValidMoves();

    // see if given square is occupied
    // 0 if no blockage
    // 1 if blocked by oposing piece
    // 2 if blocked by ally piece
    int occupationHelper(int xPos, int yPos, Board b, char myColor);

    void setCaptured(boolean captureState);

    // getters
    int[] getPosition();

    int getType();

    String getImagePath();

    Board getBoard();

    char getColor();
    
    String getName();

    Boolean getCaptured();
    
    // testing
    void printValidMoves();


}
