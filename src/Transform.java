import java.io.FileReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class Transform{

    private static void display(int[][] grid){
        for(int[] row: grid){
            StringBuilder sb = new StringBuilder();
            for(int col: row){
                sb.append(String.format(" %1$d ", col));
            }
            System.out.println(sb.toString());
            System.out.println("");
        }
        System.out.println("\n");
    }

    private static int[] reverse(int[] array){
    	int[] aux = array;
    	int size = aux.length;
    	for (int i = 0; i<size/2; i++){
    		int temp = aux[i];
    		aux[i] = aux[size-1-i];
    		aux[size-1-i] = temp;
    	}
    	return aux;
    }

    private static int[] getCol(int[][] grid, int colIndex){
    	int rows = grid.length;
		int cols = grid[0].length;
    	int[] aux = new int[rows];
    	for (int i = 0; i<rows; i++){
    		for (int j = 0; j<cols; j++){
	            if(j==colIndex) aux[i] = grid[i][j];
	        }
    	}

    	return aux;
    }

	private static int[][] reflect(int[][] grid, String axis){
		int size = grid.length;
                int[][] aux = new int[size][size];
		int mid = size/2;

		switch(axis){

			case "+x":
			for (int i =0; i<size; i++){
				for (int j=0; j<size; j++){
					aux[i][j] = grid[size-1-j][size-1-i];
				}
			}
			break;

			case "-x":
			for (int i =size-1; i>=0; i--){
				for (int j=size-1; j>=0; j--){
					aux[i][j] = grid[j][i];
				}
			}
			break;

			case "y0":
			for (int i =0; i<size; i++){
				aux[i] = grid[size-1-i];
			}
			break;

			case "x0":
			for (int i =0; i<size; i++){
				aux[i] = reverse(grid[i]);
			}
			break;

		}
		return aux;
	}

	private static int[][] rotate(int[][] grid, String angle){
		int rows = grid.length;
		int cols = grid[0].length;
		int[][] aux = new int[rows][cols];

		switch(angle){
			case "180":			
			for (int i = 0; i<rows; i++){
				aux[i] = reverse(grid[rows-1-i]);
			}
			break;

			case "90":
			aux = new int[cols][rows];
			for(int i = 0; i<cols; i++){
				aux[i] = reverse(getCol(grid,i));
			}
		}

		return aux;
	}

    private static int[][] chainRight(String file1, String file2, int padding) throws IllegalArgumentException,IOException{
                int[][] left = read(file1);
                int[][] right = read(file2);                
                if(left.length != right.length) throw new IllegalArgumentException();
                int left_cols = left[0].length;
                int right_cols = right[0].length;

                int rows = left.length;
                int cols = left_cols+right_cols+padding;
                int[][] aux = new int[rows][cols];

                for (int i = 0; i<rows; i++){
                        int[] new_row = new int[cols];
                        for (int j = 0; j<cols; j++){
                                if (j<left_cols) new_row[j] = left[i][j];
                                if (j>=left_cols && j<left_cols+padding) new_row[j] = 0;
                                if (j>=left_cols+padding) new_row[j] = right[i][j%(left_cols+padding)];
                        }
                        aux[i] = new_row;
                }
       
                return aux;
    }

    private static int[][] insert(int[][] component, int[][] grid, int x, int y){
                int[][] aux = grid;
                int rows = component.length;
                int cols = component[0].length;
                for (int start_x = x; start_x < x+rows; start_x++){
                        for (int start_y = y; start_y < y+cols; start_y++){
                                aux[start_x][start_y] = component[start_x - x][start_y - y];
                        }
                }
                return aux;
    }

	private static int[][] read(String file) throws IOException,IllegalStateException{
		BufferedReader br = new BufferedReader(new FileReader(file));
		br.mark(10000000);
		
		String line = br.readLine();
		int rows = 0;
		int cols = (line.replace(" ","")).length();

		while(line!=null || !line.equals(";;")){
			rows++;
            line = br.readLine();
		}

		br.reset();

		int[][] matrix = new int[rows][cols];

		for(int i = 0; i<rows;i++){
            String new_row = br.readLine();
            new_row = new_row.replace(" ","");
            for(int j = 0; j<cols;j++){
                if(new_row.charAt(j) == '0'){
                    matrix[i][j] = 0;
                }
                else if(new_row.charAt(j) == '1'){
                    matrix[i][j] = 1;
                }
                else{
                	throw new IllegalStateException();
                }
            }
        }

        return matrix;
	}

	private static void write(int[][] grid, String file) throws IOException{
		PrintWriter pw = new PrintWriter(file,"UTF-8");
		pw.println(">35");
		for(int[] row : grid){
			StringBuilder sb = new StringBuilder();
			for(int value : row){
				sb.append(value);
				sb.append(" ");
			}
			pw.println(sb.toString());
		}  
		pw.close();
	}

	public static void main(String[] args) throws IOException{
/*
            if(args.length == 4){
		int operation = Integer.parseInt(args[0]);
		int operation_param = Integer.parseInt(args[1]);
		String in_file = args[2];
		String out_file = args[3];

                int[][] output = new int[2][2];
		int[][] input = read(in_file);
		
		switch(operation){
			case 1:
				if( operation_param  == 90) {
					output = rotate(input,operation_param); 
					break;
				}
				if( operation_param  == 180) {
					output = rotate(input,operation_param); 
					break;
				}
				throw new IllegalArgumentException();

			case 2:
			    switch(operation_param){
			    	case 1:
			    	output = reflect(input,operation_param);
			    	break;

			    	case 2:
			    	output = reflect(input,operation_param);
			    	break;

			    	case 3:
			    	output = reflect(input,operation_param);
			    	break;

			    	case 4:
			    	output = reflect(input,operation_param);
			    	break;

			    	default:
			    	throw new IllegalArgumentException();
			    }
		}		
		write(output,out_file);
            }else if(args.length == 5){
                String op = args[0];
                String file1 = args[1];
                String file2 = args[2];
                int padding = Integer.parseInt(args[3]);
                String out_file = args[4];

                int[][] result = chainRight(file1,file2,padding);

                write(result,out_file);
            }*/
            

            int[][] test_grid = {{0,0,0,0},
                                 {0,0,0,0},
                                 {0,0,0,0}                            
                               };

            int[][] test_comp = {{1,1},
                                 {1,1}                                
                                };


            int[][] slug = read("slug.txt");
            int[][] slug_eater = read("slug_eater.txt");
            int[][] test = insert(slug,read("empty160.grid"),50,76);
            test = insert(slug_eater,test,5,70);
            test = insert(rotate(slug,"90"),test,70,80);
            test = insert(rotate(slug_eater,"90"),test,64,145);
            test = insert(rotate(slug,"180"),test,80,76);
            test = insert(rotate(slug_eater,"180"),test,145,71);
            test = insert(rotate(rotate(slug,"180"),"90"),test,70,60);
            test = insert(rotate(rotate(slug_eater,"180"),"90"),test,64,5);
            write(test,"slugparty.grid");
            
            
	}
}