import  java.util.*;
public class Suduko {
    char[][] board ;
    List<char[][]> all_sudukos;
    public Suduko() {
        all_sudukos = new ArrayList<>();
        board = new char[9][9];
        for( int i = 0 ; i < 9 ; i++)
            Arrays.fill(board[i],'.');
    }
    boolean isValidSuduko(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board[i][j]!='.' && !isValidRowCol(i,j,board[i][j])) return  false;
            }
        }
        return  true;
    }
    boolean isValidRowCol(int r , int c , char val){
        for(int i=0;i<9;i++){
            // Check Row for Already Existence of val
            if(r!=i && val==board[i][c]) return  false;
            // Check Col for Already Existence of val
            if(c!=i && val==board[r][i]) return  false;
            // Check in the 3x3 Grid
            int calr = 3 * ( r / 3 ) + ( i / 3);
            int calc = 3 * ( c / 3 ) + ( i % 3);
            if(r!=calr && c!=calc && board[calr][calc]==val) return  false;
        }
        return  true;
    }
    boolean fillSuduko(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board[i][j]=='.') {
                    for(char c = '1' ; c <= '9' ; c++){
                        if(isValidRowCol(i,j,c)){
                            board[i][j]=c;
                            if(fillSuduko()) return  true;
                            // BackTrack
                            board[i][j]='.';
                        }
                    }
                    return  false;
                }
            }
        }
        char[][] boardCopy = new char[9][9];
        for (int row = 0; row < 9; row++) {
            System.arraycopy(board[row], 0, boardCopy[row], 0, 9);
        }
        all_sudukos.add(boardCopy);
        return  true;
    }

    // Finds All Possible Valid Sudukos
    boolean fillSuduko_Rec(int r , int c){
        if(r==9){
            char[][] boardCopy = new char[9][9];
            for (int row = 0; row < 9; row++) {
                System.arraycopy(board[row], 0, boardCopy[row], 0, 9);
            }
            all_sudukos.add(boardCopy);
            return  false;
        }
        if(c==9) return fillSuduko_Rec(r+1,0);
        if(board[r][c]!='.') return fillSuduko_Rec(r,c+1);
        else {
            for(char num = '1' ; num <= '9' ; num++){
                if(isValidRowCol(r,c,num)){
                    board[r][c]=num;
                    fillSuduko_Rec(r,c+1);
                    // BackTrack
                    board[r][c]='.';
                }
            }
            return  false;
        }
    }

    public void printSuduko(char[][] c){
        for(int i = 0 ; i < 9 ; i++ ) {
            for(int j = 0 ; j < 9 ; j++ ) {
                System.out.print(c[i][j]+" ");
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("-------------------------");
        System.out.println("Welcome TO Suduko Solver ");
        System.out.println("-------------------------");
        System.out.println("Select Type of Insertion of Data");
        System.out.println("1. Insert Elements at Positions");
        System.out.println("2. Insert Sequencially (Empty Boxes represnt with '.' )");
        int type = sc.nextInt();
        Suduko suduko = new Suduko();
        if(type==1) {
            System.out.println("Example for Insertion : 1(Row) 2(Col) 3(Value)");
            System.out.println("Example for Exit : -1(Row) -1(Col) X(Value)");
            System.out.println("After Insertion completion Type : \" X \"");
            while (true) {
                int r = sc.nextInt(), c = sc.nextInt();
                char val = sc.next().charAt(0);
                if (r == -1 && c == -1 && val == 'X') break;
                if (r < 0 || r > 8 || c < 0 || c > 8 || val < '1' || val > '9') {
                    System.out.println("Invalid Input ");
                } else {
                    suduko.board[r][c] = val;
                }

            }
        }
        else{
            for(int i = 0 ; i < 9 ; i++){
                for(int j = 0 ; j < 9 ; j++){
                    char val = sc.next().charAt(0);
                    if((val<'1' || val>'9') && val!='.' ) {
                        System.out.println("Invalid Character Start again Filling the board from Start RESTART THE PROGRAM");
                        return;
                    }
                    else{
                        suduko.board[i][j] = val;
                    }
                }
            }
        }
        if(!suduko.isValidSuduko()) {
            System.out.println("Suduko is Not Valid One");
            return;
        }
        suduko.fillSuduko();
        int n = suduko.all_sudukos.size();
        if(n>1) {
            System.out.println("More than One Suduko is Possible");
        }

        for(int sud = 0 ; sud < n ; sud++){
            System.out.println("------------------------------------------------");
            char[][] board = suduko.all_sudukos.get(sud);
            suduko.printSuduko(board);
            System.out.println("------------------------------------------------");
        }
    }
}