import java.util.ArrayList;
import java.util.Scanner;

public class DietModel {
    double weight;
    double height;
    int age;
    String gender;
    double activityLevel;
    double caloriesPerDay;
    // Make 3 lists, one for each type of food (protein, fat, carbs)
    ArrayList<Foods> proteinFoods = new ArrayList<Foods>();
    ArrayList<Foods> fatFoods = new ArrayList<Foods>();
    ArrayList<Foods> carbFoods = new ArrayList<Foods>();

    // Initialize the lists in the constructor with the appropriate foods (only one
    // nutrition value per food i.e. protein, fat, or carbs) every other value is 0
    // except for calories
    public DietModel() {
        this.weight = 0;
        this.height = 0;
        this.age = 0;
        this.gender = "";
        this.activityLevel = 0;
        this.caloriesPerDay = 0;
        // Add foods to the protein list
        proteinFoods.add(new Foods("Chicken breast", 1650, 310, 36, 0, 0));
        proteinFoods.add(new Foods("Beef", 2534, 260, 166, 0, 0));
        proteinFoods.add(new Foods("Fish", 1308, 264, 27, 0, 0));
        proteinFoods.add(new Foods("Eggs", 1344, 120, 96, 0, 0));
        // Add foods to the fat list
        fatFoods.add(new Foods("Butter", 7140, 8, 840, 1, 0));
        fatFoods.add(new Foods("Olive Oil", 8330, 0, 980, 0, 0));
        fatFoods.add(new Foods("Avocado", 1610, 20, 145, 85, 0));
        fatFoods.add(new Foods("Coconut Oil", 8470, 0, 910, 0, 0));
        // Add foods to the carb list
        carbFoods.add(new Foods("Rice", 1300, 16, 4, 300, 0));
        carbFoods.add(new Foods("Pasta", 1538, 58, 10, 304, 0));
        carbFoods.add(new Foods("Bread", 2630, 91, 34, 490, 0));
        carbFoods.add(new Foods("Potatoes", 1001, 26, 1, 222, 0));
    }

    // Add a method to calculate the BMR
    private double calculateBMR() {
        if (gender == "Male") {
            return 10 * weight + 6.25 * height - 5 * age + 5;
        } else {
            return 10 * weight + 6.25 * height - 5 * age - 161;
        }
    }

    // Method to calculate TDEE
    private double calculateTDEE() {
        return calculateBMR() * activityLevel ;
    }

    //Method to calculate Calories per day
    private double calculateCaloriesPerDay() {
        
    	double caloriesPerDay = calculateTDEE() - 500;  
    	System.out.println(caloriesPerDay+" Daily calories to deficit");
    	return caloriesPerDay;
    }

    // Method to set the calories per day
    public void setCaloriesPerDay() {
        caloriesPerDay = calculateCaloriesPerDay();
    }

    // Setters for the user's information
    public boolean setAge(int age) {
        if (age <= 0) {
            return false;
        }
        this.age = age;
        return true;
    }

    public boolean setWeight(double weight) {
        if (weight <= 0) {
            return false;
        }
        this.weight = weight;
        return true;
    }

    public boolean setHeight(double height) {
        if (height <= 0) {
            return false;
        }
        this.height = height;
        return true;
    }

    public boolean setGender(int genderChoice) {
        switch (genderChoice) {
            case 1:
                gender = "Male";
                return true;
            case 2:
                gender = "Female";
                return true;
            default:
                return false;
        }
    }

    public boolean setActivityLevel(int actLevel) {
        switch (actLevel) {
            case 1:
                this.activityLevel = 1;            
                return true;
            case 2:
                this.activityLevel = 1.2;
                return true;
            case 3:
                this.activityLevel = 1.375;
                return true;
            case 4:
                this.activityLevel = 1.55;
                return true;
            case 5:
                this.activityLevel = 1.725;
                return true;
            default:
                return false;
        }
    }

    // Method to check if the food choices are valid
    public boolean checkFoodChoices(int protein, int fat, int carbs) {
        if (protein < 1 || protein > 4 || fat < 1 || fat > 4 || carbs < 1 || carbs > 4) {
            return false;
        }
        return true;
    }

    public void setupDiet(int protein, int fat, int carbs) {
        // Calculate the calories per meal
        double caloriesPerMeal = caloriesPerDay / 3; // 3 meals per day
        // Get the selected food from each list
        Foods selectedProtein = proteinFoods.get(protein - 1); 
        Foods selectedFat = fatFoods.get(fat - 1); 
        Foods selectedCarb = carbFoods.get(carbs - 1); 

        // Calculate calories assigned to each macronutrient group based on the target
        double proteinCalories = caloriesPerMeal * 0.35; // 35% of calories from protein
        double fatCalories = caloriesPerMeal * 0.25; // 25% of calories from fats
        double carbCalories = caloriesPerMeal * 0.4; // 40% of calories from carbs

        // Calculate the amount (in grams) of each selected food
        // Note: The Foods class needs to have accurate caloriesPerKg values for each food
        
        double proteinAmount = (proteinCalories / selectedProtein.caloriesPerKg) * 1000; // Convert to grams
        double fatAmount = (fatCalories / selectedFat.caloriesPerKg) * 1000; // Convert to grams
        double carbAmount = (carbCalories / selectedCarb.caloriesPerKg) * 1000; // Convert to grams

        // Output the recommended quantities
        System.out.println("Recommended quantites to reach a calorie deficit: "); //1 meal
        System.out.println(String.format("%.2f grams of %s", proteinAmount, selectedProtein.name));
        System.out.println(String.format("%.2f grams of %s", fatAmount, selectedFat.name));
        System.out.println(String.format("%.2f grams of %s", carbAmount, selectedCarb.name));
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            DietModel diet = new DietModel();
            System.out.println("Enter your weight in kg: ");
            double weight = scanner.nextDouble();
            if (!diet.setWeight(weight)) {
                System.out.println("Invalid weight");
                return;
            }
            System.out.println("Enter your height in cm: ");
            double height = scanner.nextDouble();
            if (!diet.setHeight(height)) {
                System.out.println("Invalid height");
                return;
            }
            System.out.println("Enter your age: ");
            int age = scanner.nextInt();
            if (!diet.setAge(age)) {
                System.out.println("Invalid age");
                return;
            }
            System.out.println("Enter your gender (1 for male, 2 for female)): ");
            int genderChoice = scanner.nextInt();
            if (!diet.setGender(genderChoice)) {
                System.out.println("Invalid age");
                return;
            }
            System.out.println("Enter your activity level: ");
            System.out.println("1. No activities ");
            System.out.println("2. 1 to 3 Training days per week ");
            System.out.println("3. 3 to 5 Training days per week ");
            System.out.println("4. 6 to 7 Training days per week ");
            System.out.println("5. Hard training every day of the week ");
            int actLevel = scanner.nextInt();
            if (!diet.setActivityLevel(actLevel)) {
                System.out.println("Invalid activity level");
                return;
            }
            diet.setCaloriesPerDay();
            System.out.println(
                    "Enter the type of protein food you want to eat (1 for Chicken, 2 for Beef, 3 for Fish, 4 for Eggs): ");
            int proteinChoice = scanner.nextInt();
            System.out.println(
                    "Enter the type of fat food you want to eat (1 for Butter, 2 for Olive Oil, 3 for Avocado, 4 for Coconut Oil): ");
            int fatChoice = scanner.nextInt();
            System.out.println(
                    "Enter the type of carb food you want to eat (1 for Rice, 2 for Pasta, 3 for Bread, 4 for Potatoes): ");
            int carbChoice = scanner.nextInt();
            if (!diet.checkFoodChoices(proteinChoice, fatChoice, carbChoice)) {
                System.out.println("Invalid food choices");
                return;
            }
            diet.setupDiet(proteinChoice, fatChoice, carbChoice);
        }

    }
}
