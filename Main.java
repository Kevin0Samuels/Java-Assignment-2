import java.util.Scanner;

class Calculator_2 {
    int add(int a, int b){
        return a+b;
    }
    double add(double a, double b){
        return a+b;
    }
    int subtract(int a, int b){
        return a-b;
    }
    double multiply(double a, double b){
        return a*b;
    }
    int divide(int a, int b){
        if(a<b || a==0 || b==0){
            System.out.println("INVALID INPUT");
        }
        return a/b;
    }
}

class Interface extends Calculator_2{
    Scanner sc = new Scanner(System.in);
    void performAddition(){
        System.out.println("Do you want to add integers or decimal number");
        System.out.println("Enter 1 for integers");
        System.out.println("Enter 2 for decimal or other numbers");
        int n = sc.nextInt();
        if(n==1){
            System.out.println("Enter first number to add");
            int a = sc.nextInt();
            System.out.println("Enter second number to add");
            int b = sc.nextInt();
            int result = super.add(a,b);
            System.out.println("Result: " + result);
        }
        if(n==2){
            System.out.println("Enter first number to add");
            double a = sc.nextDouble();
            System.out.println("Enter second number to add");
            double b = sc.nextDouble();
            double result = super.add(a,b);
            System.out.println("Result: " + result);
        }
    }

    void performSubtraction(){
        System.out.println("Enter first number to subtract");
        int a = sc.nextInt();
        System.out.println("Enter second number to subtract");
        int b = sc.nextInt();
        int result = super.subtract(a,b);                                        // important point
        System.out.println("Result: " + result);
    }

    void performMultiplication(){
        System.out.println("Enter first number to multiply");
        double a = sc.nextDouble();
        System.out.println("Enter second number  to multiply");
        double b = sc.nextDouble();
        double result = super.multiply(a,b);
        System.out.println("Result: " + result);
    }

    void performDivision(){
        System.out.println("Enter first number to divide");
        int a = sc.nextInt();
        System.out.println("Enter second number to divide");
        int b = sc.nextInt();
        int result = super.divide(a,b);
        System.out.println("Result: " + result);
    }

    void mainMenu(){
        System.out.println("Welcome to the Calculator Application! ");
        System.out.println("1. Add Numbers ");
        System.out.println("2. Subtract Numbers");
        System.out.println("3. Multiply Numbers ");
        System.out.println("4. Divide Numbers ");
        System.out.println("5. Exit");
        System.out.println("Enter your choice: ");
        int n = sc.nextInt();
        if(n==1){
            performAddition();
        }

        else if(n==2){
            performSubtraction();
        }
        else if(n==3){
            performMultiplication();
        }
        else if(n==4){
            performDivision();
        }
        else if(n==5){
            System.out.println("GOOD BYE!!!");
        }
        else{
            System.out.println("INVALID CHOICE");
        }
    }
}

class Main{
    public static void main(String[] args) {
        Interface obj = new Interface();
        obj.mainMenu();
    }
}