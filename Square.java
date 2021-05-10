public class Square {
    private final int xPosition;
    private final int yPosition;
    private final char color; // L for light D for dark
    
    private boolean highlightedStart; // is the current square selected
    private boolean highlightedTarget; // current square selected as target
    
    private static Square highlightedStartSquare; // the current selected start square
    private static int[] highlightedTargetSquare; // the current selected target square (if invalid)
    private Piece occupant; // the piece occupying this square (or null)

    /*
    * Input: x coordinate, y coordinate, square color
    * Ouptput: None
    * Description: Initializes a chess board square
    */       
    public Square(int x, int y, char color) {
        this.xPosition = x;
        this.yPosition = y;
        this.color = color;
        this.highlightedStart = false;
        this.highlightedTarget = false;
        this.occupant = null;
    }

   /*
    * Input: None
    * Ouptput: None
    * Description: Draws the current board square
    */    
    public void draw() {
        colorHelper();
        drawSquare();
    }

    public void drawSquare() {
        PennDraw.filledSquare(this.xPosition + 0.5, this.yPosition + 0.5,
        0.5 );
    }

   /*
    * Input: None
    * Ouptput: None
    * Description: Helps determine the appropriate board color
    */        
    private void colorHelper() {
        if (this.color == 'd') {
            PennDraw.setPenColor(153, 102, 0);
        }
        else {
            PennDraw.setPenColor(255, 204, 102);
        }
    }

   /*
    * Input: None
    * Ouptput: None
    * Description: Prints the currents squares coordinates
    */  
    public String toString() {
        String outString = "(" + this.xPosition + ", " + this.yPosition + ")";
        return outString;
    }

   /*
    * Input: Square selected by user
    * Ouptput: None
    * Description: Changes the color of the current square to 
    *              light green to indicate selection
    */      
    public static void setHighlightedStartSquare(Square s) {
        // change back the currently highlighted square
        if (highlightedStartSquare != null) {
            highlightedStartSquare.draw();
            Piece p = highlightedStartSquare.occupant;
            highlightedStartSquare.occupant.draw(p.getPosition(), p.getImagePath());
        }

        // highlight the new square
        if (s.occupant != null) {
            highlightedStartSquare = s;
            PennDraw.setPenColor(179, 255, 204);
            s.drawSquare();
            Piece p = s.occupant;
            s.occupant.draw(p.getPosition(), p.getImagePath());
        }
    }    

    // highlight king square if king is in check
    public void highlightCheck() {
        PennDraw.setPenColor(255, 102, 102);
        this.drawSquare();
        this.occupant.draw(this.occupant.getPosition(), this.occupant.getImagePath());
    }


    public static void clearHighlightedStartSquare() {
        highlightedStartSquare.draw();
        setHighlightedStartSquareNull();
    }
    
    // getters

    public Piece getOccupant() {
        return this.occupant;
    }

    public static Square getHighlightedStartSquare() {
        return highlightedStartSquare;
    }

    public int getXposition() {
        return this.xPosition;
    }

    public int getYposition() {
        return this.yPosition;
    }

    public int[] getPosition() {
        int position[] = {this.xPosition, this.yPosition};
        return position;
    }
    // setters

    public void setOccupant(Piece p) {
        this.occupant = p;
    }

    public static void setHighlightedStartSquareNull() {
        highlightedStartSquare = null;
    }
}

