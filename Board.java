/*
* Yonatan Babore 1/2/21
* Description: 
* 
*/
import java.util.ArrayList;

public class Board {

    // 2d array containing each chess square
    public ArrayList<Square[]> squares = new ArrayList<Square[]>();
    public ArrayList<Piece> pieces = new ArrayList<Piece>();
    public ArrayList<King> kings = new ArrayList<King>();
    private char turn = 'W';
    private static int moveCount = 0;

    public Board() {
        squares.clear();
        pieces.clear();
        int counter = 0;
        for (int col = 0; col < 8; col++) {
            counter++;
            Square[] curRow = new Square[8];
            for (int row = 0; row < 8; row++) {
                
                // set the current square color
                char color;
                if (counter % 2 == 0) {
                    color = 'l';
                }
                else {
                    color = 'd';
                }

                curRow[row] = new Square(col, row, color);
                counter++;
            }
            squares.add(curRow);
        }
    }

    /*
    * Input: None
    * Ouptput: None
    * Description: Draws the chess board
    */
    public void drawBoard() {
        for (int col = 0; col < this.squares.size(); col++) {
            for (int row = 0; row < this.squares.get(col).length; row++) {
                this.squares.get(col)[row].draw();
                try {
                    Thread.sleep(10);
                }
                catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /*
    * Input: None
    * Ouptput: None
    * Description: Prepares Canvas with appropriate scale
    */
    private void prepareCanvas() {
        PennDraw.setCanvasSize(640, 760);
        PennDraw.setXscale(0, 8);
        PennDraw.setYscale(0, 9.5);  
        PennDraw.setPenColor(51, 204, 255);
        PennDraw.filledRectangle(4, 8.75, 4, .75);
        PennDraw.setPenColor(PennDraw.WHITE);
        PennDraw.text(1, 9.25, "White Captures");
        PennDraw.setPenColor(PennDraw.BLACK);  
        PennDraw.text(7, 9.25, "Black Captures");
    }

    /*
    * Input: None
    * Ouptput: Returns the square that was clicked on
    * Description: Detects if the mouse is clicked on the board
    */
    private Square detectMousePressEvent() {
        int[] position1 = new int[2];
        Square curSquare = null;
        // if a first square is selected highlight it
        if (PennDraw.mousePressed()) {
            position1[0] = (int) PennDraw.mouseX();
            position1[1] = (int) PennDraw.mouseY();
            curSquare = this.squares.get(position1[0])[position1[1]];
        }
        return curSquare;
    }

    /*
    * Input: A square object
    * Ouptput: None
    * Description: Checks to see if current square (destination) is occupied
    *              if so, it perfoms a piece capture
    */
    public void captureProtocol(Square curSquare) {
        // if there is already a highlighted square and the selected square is occupied
        if (Square.getHighlightedStartSquare() != null && (curSquare.getOccupant() != null)) {
            Square origin = Square.getHighlightedStartSquare();
            Piece capturingPiece = origin.getOccupant();
            Piece targetPiece = curSquare.getOccupant();

            // square clicked on is occupied but is not the highlighted square
            if (curSquare.getOccupant() != capturingPiece) {
                // confirm it is an enemy piece
                if (targetPiece.getColor() != capturingPiece.getColor()) {
                    // if it is the capturing pieces turn 
                    if (capturingPiece.getColor() == turn) {
                        // if capturing the current piece is a valid move
                        if (comparisonHelper(capturingPiece.findValidMoves(), curSquare.getPosition())) {
                            curSquare.draw();
                            capturingPiece.capture(capturingPiece.getBoard(), curSquare, capturingPiece.getPosition(), capturingPiece.getImagePath());
                            targetPiece.setCaptured(true);
                            changeTurn();                        
                            moveCount++;
                            checkProtocol();
                            System.out.print(moveCount);
                        }
                    }
                }
            }
        }
    }

    /*
    * Input: None
    * Ouptput: None
    * Description: Checks to see if either king is now in check
    */
    public void checkProtocol() {
        for (int i = 0; i < kings.size(); i++) {
            kings.get(i).findCheck();
        }
    }

    /*
    * Input: A square object
    * Ouptput: None
    * Description: If the selected square (destination) is unoccupied, it moves the 
    *               piece there
    */
    public void moveProtocol(Square curSquare) {
        // if this square does not have an occupant
        if (curSquare.getOccupant() == null) {
            // if there was a previously selected square
            if (Square.getHighlightedStartSquare() != null) {
                Piece curPiece = Square.getHighlightedStartSquare().getOccupant();
                // if it is the current piece's turn
                if (curPiece.getColor() == turn) {
                    // let the pice make a valid move
                    if (comparisonHelper(curPiece.findValidMoves(), curSquare.getPosition())) {
                        curPiece.move(curPiece.getBoard(), curSquare, curPiece.getPosition(), curPiece.getImagePath());
                        changeTurn();
                        moveCount++;
                        checkProtocol();
                        System.out.println("MOVE: " + moveCount);
                    }
                }
            }
            Square.setHighlightedStartSquareNull();
        }
    }

    /*
    * Input: None
    * Ouptput: None
    * Description: Makes a move after a piece and square are selected
    */
    private void makeMove() {

        Square curSquare = detectMousePressEvent();

        // see if the king is in check before the move is made
        // check for valid square selection
        if (curSquare != null) {
            captureProtocol(curSquare);
            if (curSquare.getOccupant() != null) {
                if (curSquare.getOccupant().getColor() == turn) {
                    Square.setHighlightedStartSquare(curSquare);
                }
            }
            moveProtocol(curSquare);
        }
    }

    /*
    * Input: Arraylist of valid moves (integer arrays), an integer array of target square coordinates
    * Ouptput: None
    * Description: Checks to see if a desired move is valid
    */
    private Boolean comparisonHelper(ArrayList<int[]> validMoves, int[] target) {
        int xValue = target[0];
        int yValue = target[1];

        for (int i = 0; i < validMoves.size(); i++) {
            int tempX = validMoves.get(i)[0];
            int tempY = validMoves.get(i)[1];

            if ((xValue == tempX) && (yValue == tempY)) {
                return true;
            }
        }
        return false;
    }

    /*
    * Input: None
    * Ouptput: None
    * Description: Changes turn
    */
    public void changeTurn() {
        // take the king out of check before turn ends
        unCheckKing();

        if (turn == 'W') {
            turn = 'B';
        }
        else {
            turn = 'W';
        }
    }

    /*
    * Input: None
    * Ouptput: None
    * Description: Places pieces on the board to start the game
    */
    public void placePieces() {

        // add all pawns to board
        for (int i = 0; i < 8; i++) {
            Pawn blackPawn = new Pawn(this, 'B', i, 1);
            Pawn whitePawn = new Pawn(this, 'W', i, 6);
            blackPawn.draw(blackPawn.getPosition(), blackPawn.getImagePath());
            whitePawn.draw(whitePawn.getPosition(), whitePawn.getImagePath());
            this.pieces.add(blackPawn);
            this.pieces.add(whitePawn);
        }

        // place mid leve pieces
        for (int i = 0; i < 3; i++) {
            int j = 7 - i;

            //place rooks
            if (i == 0) {
                for (int k = 0; k < 2; k++) {
                    char color;
                    int row;
                    if (k == 0) {
                        color = 'B';
                        row = 0;
                    }
                    else {
                        color = 'W';
                        row = 7;
                    }
                    Rook newPieceLeft = new Rook(this, color, i, row);
                    newPieceLeft.draw(newPieceLeft.getPosition(), newPieceLeft.getImagePath());
                    Rook newPieceRight = new Rook(this, color, j, row);
                    newPieceRight.draw(newPieceRight.getPosition(), newPieceRight.getImagePath());
                    this.pieces.add(newPieceLeft);
                    this.pieces.add(newPieceRight);
                }
 
            }

            // place knights
            if (i == 1) {
                for (int k = 0; k < 2; k++) {
                    char color;
                    int row;
                    if (k == 0) {
                        color = 'B';
                        row = 0;
                    }
                    else {
                        color = 'W';
                        row = 7;
                    }     
                    Knight newPieceLeft = new Knight(this, color, i, row);
                    newPieceLeft.draw(newPieceLeft.getPosition(), newPieceLeft.getImagePath());
                    Knight newPieceRight = new Knight(this, color, j, row);
                    newPieceRight.draw(newPieceRight.getPosition(), newPieceRight.getImagePath());     
                    this.pieces.add(newPieceLeft);
                    this.pieces.add(newPieceRight);    
                }
            }

            // place Bishops
            if (i == 2) {
                for (int k = 0; k < 2; k++) {
                    char color;
                    int row;
                    if (k == 0) {
                        color = 'B';
                        row = 0;
                    }
                    else {
                        color = 'W';
                        row = 7;
                    }     
                    Bishop newPieceLeft = new Bishop(this, color, i, row);
                    newPieceLeft.draw(newPieceLeft.getPosition(), newPieceLeft.getImagePath());
                    Bishop newPieceRight = new Bishop(this, color, j, row);
                    newPieceRight.draw(newPieceRight.getPosition(), newPieceRight.getImagePath());         
                    this.pieces.add(newPieceLeft);
                    this.pieces.add(newPieceRight);
                }
            }
        }

        // add the queens (they go on the square matching their color 4th)
        Queen whiteQueen = new Queen(this, 'W', 4, 7);
        whiteQueen.draw(whiteQueen.getPosition(), whiteQueen.getImagePath());
        Queen blackQueen = new Queen(this, 'B', 4, 0);
        whiteQueen.draw(blackQueen.getPosition(), blackQueen.getImagePath());
        this.pieces.add(whiteQueen);
        this.pieces.add(blackQueen);
        
        // add the kings
        King whiteKing = new King(this, 'W', 3, 7);
        whiteKing.draw(whiteKing.getPosition(), whiteKing.getImagePath());
        kings.add(whiteKing);
        King blackKing = new King(this, 'B', 3, 0);
        whiteKing.draw(blackKing.getPosition(), blackKing.getImagePath());
        kings.add(blackKing);
        this.pieces.add(blackKing);
        this.pieces.add(whiteKing);
    }

    /*
    * Input: x and y coordinates as integers
    * Ouptput: None
    * Description: Locates the square object with the given coordinates
    */
    public Square squareFinder(int xPos, int yPos) {
        for (int i = 0; i < this.squares.size(); i++) {
            Square[] curRow = this.squares.get(i);
                for (int j = 0; j < curRow.length; j++) {
                    Square curSquare = curRow[j];
                    if (curSquare.getXposition() == xPos && curSquare.getYposition() == yPos) {
                        return curSquare;
                    }
                }
            }
            return null;
        }
    /*
    * Input: A char ('w' or 'b') representing white or black pieces
    * Ouptput: None
    * Description: Finds all the pieces for a given side
    */
    public ArrayList<Piece> getPieces(char color) {
        ArrayList<Piece> foundPieces = new ArrayList<Piece>();
        for (int i = 0; i < this.pieces.size(); i++) {
            if (pieces.get(i).getColor() == color) {
                foundPieces.add(pieces.get(i));
            }
        }
        return foundPieces;
    }

    /*
    * Input: None
    * Ouptput: None
    * Description: Unhighlights the king if it is highlighted for being in check
    */
    public void unCheckKing() {
        for (int i = 0; i < this.kings.size(); i++) {
            King curKing = this.kings.get(i);
            if (curKing.getCheck()) {
                curKing.setCheck(false);
                Square curSquare = squareFinder(curKing.getPosition()[0], curKing.getPosition()[1]);
                curSquare.draw();
                curKing.draw(curKing.getPosition(), curKing.getImagePath());
            }
        }
    }
    public static void main(String[] args){
        Board b = new Board();
        b.prepareCanvas();
        b.drawBoard();
        b.placePieces();
        

        while (true) {
            b.makeMove(); 
        }
    }

}
