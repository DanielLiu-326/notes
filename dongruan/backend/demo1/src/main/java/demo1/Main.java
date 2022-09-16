package demo1;

public class Main {
    public static void print_n_ch(String a,int i){
        for(int j = 0;j<i;j++){
            System.out.print(a);
        }
    }
    public static void main(String[] args) {
        for(int i = 1;i<=9;i++){
            for(int j = 1;j<=i;j++) {
                System.out.print(j);
                System.out.print("*");
                System.out.print(i);
                System.out.print("=");
                System.out.print(i * j);
                System.out.print("\t");
            }
            System.out.print("\n");
        }

        int input = 5;
        for (int i = 0;i<input;i++){
            print_n_ch("   ",i);
            print_n_ch(" * ",1);
            print_n_ch("   ",input-i-1);
            print_n_ch(" * ",1);
            print_n_ch("   ",input-i-1);
            print_n_ch(" * ",1);
            print_n_ch("\n",1);
        }
        print_n_ch(" * ",input*2+1);
        print_n_ch("\n",1);
        for (int i = 0;i<input;i++){
            print_n_ch("   ",input-i-1);
            print_n_ch(" * ",1);
            print_n_ch("   ",i);
            print_n_ch(" * ",1);
            print_n_ch("   ",i);
            print_n_ch(" * ",1);
            print_n_ch("\n",1);
        }
    }
}
