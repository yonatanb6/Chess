import java.util.ArrayList;

public abstract class Piece implements PieceInterface {

    public static int whitePiecesCaptured = 0; // 
    public static int blackPiecesCaptured = 0;

    @Override
    public void draw(int[] position, String imagePath) {
        PennDraw.picture(position[0] + .5, position[1] + .5, imagePath, 80, 80);
    }

    public void drawCaptured(Piece p) {

        // white pice captured
        if (p.getColor() == 'W') {
            double height = 8.9;
            double shift = 0;
            if (whitePiecesCaptured >= 8) {
                height = 8.3;
                shift = -8;
            }
            PennDraw.picture(5 + (0.4 * whitePiecesCaptured % 8), height, p.getImagePath(), 35, 35);
            whitePiecesCaptured++;
        }   
        else {
            double height = 8.9;
            double shift = 0;
            if (blackPiecesCaptured >= 8) {
                height = 8;
                shift = -8;
            }
            PennDraw.picture(.4 + (0.4 * blackPiecesCaptured % 8), height, p.getImagePath(), 35, 35);
            blackPiecesCaptured++;
        }
    }

    @Override
    public void move(Board b, Square target, int[] position, String imagepath) {
        // clear old square of this pice
        Square curSquare = b.squares.get(position[0])[position[1]];
        
        // find out if its a pawn
        Piece piece = curSquare.getOccupant();

        curSquare.setOccupant(null);
        curSquare.draw();

        // move to new square
        target.setOccupant(this);
        position[0] = target.getXposition();
        position[1] = target.getYposition();
        draw(position, imagepath);
        Square.clearHighlightedStartSquare();

        if (piece.getType() == 6) {
            piece.setFirstMove();
        }
    }

    public void capture(Board b, Square target, int[] position, String imagepath) {
        // remove captured piece from the target square
        Piece capturedPiece = target.getOccupant();
        drawCaptured(capturedPiece);
        move(b, target, position, imagepath);
    }

    public ArrayList<int[]> diagonalHelperTopLeftBottomRight(int[] position, Board board, char color) {
        ArrayList<int[]> validMoves = new ArrayList<int[]>();

        // moves towards top left
        int curX = position[0];
        int curY = position[1];
        while ((curX > 0) && (curY < 7)) {
            curX --;
            curY ++;

            // if a piece is blocking
            int[] curMove = new int[2];
            curMove[0] = curX;
            curMove[1] = curY;
            validMoves.add(curMove);  
        }

        curX = position[0];
        curY = position[1];

        // moves towards bottom right
        while ((curX < 7) && (curY > 0)) {
            curX ++;
            curY --;
            int[] curMove = new int[2];
            curMove[0] = curX;
            curMove[1] = curY;

            // make sure the square is unoccupied
            Square curSquare = board.squareFinder(curX, curY);
            if (curSquare.getOccupant() != null) {
                validMoves.add(curMove);
                detectCheck(board, curX, curY);
                break;
            }
            validMoves.add(curMove);  
        }        
        return validMoves;
    }

    public ArrayList<int[]> diagonalHelperBottonLeftTopRight(int[] position, Board board, char color) {
        ArrayList<int[]> validMoves = new ArrayList<int[]>();

        // moves towards bottom left
        int curX = position[0];
        int curY = position[1];
        while ((curX > 0) && (curY > 0)) {
            curX --;
            curY --;
            int blocked = occupationHelper(curX, curY, board, color);
            if (blocked == 2) {
                break;
            }
            int[] curMove = new int[2];
            curMove[0] = curX;
            curMove[1] = curY;
            validMoves.add(curMove);  
            if (blocked == 1) {
                detectCheck(board, curX, curY);
                break;
            }
        }

        curX = position[0];
        curY = position[1];

        // moves towards top right
        while ((curX < 7) && (curY < 7)) {
            curX ++;
            curY ++;
            int blocked = occupationHelper(curX, curY, board, color);
            if (blocked == 2) {
                break;
            }
            int[] curMove = new int[2];
            curMove[0] = curX;
            curMove[1] = curY;
            validMoves.add(curMove);  
            if (blocked == 1) {
                detectCheck(board, position[0], curY);
                break;
            }
        }        
        return validMoves;

    }


    public ArrayList<int[]> verticalHelper(int[] position, Board board, char color) {
        ArrayList<int[]> validMoves = new ArrayList<int[]>();

        // moves down
        int curY = position[1];
        while (curY > 0) {
            curY --;
            int blocked = occupationHelper(position[0], curY, board, color);
            if (blocked == 2) {
                break;
            }

            int[] curMove = new int[2];
            curMove[0] = position[0];
            curMove[1] = curY;
            validMoves.add(curMove);  
            if (blocked == 1) {
                detectCheck(board, position[0], curY);
                break;
            }
        }

        // moves up
        curY = position[1];
        while (curY < 7) {
            curY ++;
            int blocked = occupationHelper(position[0], curY, board, color);
            if (blocked == 2) {
                break;
            }
            int[] curMove = new int[2];
            curMove[0] = position[0];
            curMove[1] = curY;
            validMoves.add(curMove);  
            if (blocked == 1) {
                detectCheck(board, position[0], curY);
                break;
            }
        }  
        return validMoves;
    }

    public ArrayList<int[]> horizontalHelper(int[] position, Board board, char color) {
        ArrayList<int[]> validMoves = new ArrayList<int[]>();

        // moves left
        int curX = position[0];
        while (curX > 0) {
            curX --;
            int blocked = occupationHelper(curX, position[1], board, color);
            if (blocked == 2) {
                break;
            }
            int[] curMove = new int[2];
            curMove[0] = curX;
            curMove[1] = position[1];
            validMoves.add(curMove);  
            if (blocked == 1) {
                detectCheck(board, curX, position[1]);
                break;
            }
        }

        // moves right
        curX = position[0];
        while (curX < 7) {
            curX ++;
            int blocked = occupationHelper(curX, position[1], board, color);
            if (blocked == 2) {
                break;
            }
            int[] curMove = new int[2];
            curMove[0] = curX;
            curMove[1] = position[1];
            validMoves.add(curMove);  
            if (blocked == 1) {
                detectCheck(board, curX, position[1]);
                break;
            }
        }
        return validMoves;
    }
    
    @Override
    public int occupationHelper(int xPos, int yPos, Board b, char myColor) {
        Piece p = b.squares.get(xPos)[yPos].getOccupant();
        
        // if there is a piece in the way...
        if (p != null) {
            char pieceColor = p.getColor();

            // ally piece
            if (pieceColor == myColor) {
                return 2;
            }
            else {
                return 1;
            }

        }
        return 0;
    }

    public void printValidMoves() {
        ArrayList<int[]> validMoves = findValidMoves();
        for (int i = 0; i < validMoves.size(); i++) {
            System.out.println("Move " + i + " (" + validMoves.get(i)[0] + ", " + validMoves.get(i)[1] + ")");
        }
    }

    public void detectCheck(Board board, int x, int y) {
        // check to see if this puts the king in check
        Square curSquare = board.squares.get(x)[y];
        if (curSquare.getOccupant() != null) {
            if (curSquare.getOccupant().getName() == "King") {
                System.out.println("CHECK!");
                King king = (King) curSquare.getOccupant();
                king.setCheck();
                king.setCheckingPiece(this);
            }
        }
    }
    // setters

    // just for the pawn
    public void setFirstMove() {
        
    }

}
