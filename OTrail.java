import java.util.Random;
import java.util.Scanner;

public class Main
{

    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args)
    {
      
        Person leader = ChoosePerson();
        leader.CreateParty(5);


        Trail trail = new Trail();

        boolean inPlay = true;
        while (inPlay)
        {
            trail.DisplayChoices(leader);

            if(trail.Locations[trail.CurrentStop] == "Oregon City")
            {
                System.out.println("Congratulations!");
                System.out.println(leader.Name + " "+leader.GetActiveCompanions());
                System.out.println("You've completed the Oregon Trail.");
                System.out.println("Total number of Days on the Trail: " + trail.TotalDays);
                System.out.println("Amount of Food in the Wagon: " + leader.Food);
                System.out.println("Number of Oxen: " + leader.Oxen);

                inPlay = false;

            }
        }


    }

    public static Person ChoosePerson()
    {
        int choice;
        System.out.println("Many Kinds of People Made the Trip to Oregon");
        System.out.println("You May:");
        System.out.println("1.) Be a Banker in Boston");
        System.out.println("2.) Be a Carpenter from Ohio");
        System.out.println("3.) Be a Farmer from Illinois");
        System.out.println("Please enter your choice:");
        choice = scanner.nextInt();


        if (choice == 1)
        {
            return new Banker();
        }
        else if (choice == 2)
        {
            return new Carpenter();
        }
        else if (choice == 3)
        {
            return new Farmer();
        }
        else
        {
            System.out.println("Incorrect choice. Please try again.");
            return ChoosePerson();
        }
    }
}
class Person
{
    public String Profession;
    public String Name;
    public double Money;
    public int Oxen;
    public int Food;
    public Boolean Alive = true;

    public Person[]  Companions;

    public Person()
    {

    }

    public void CreateParty(int partySize)
    {
        Scanner scanner = new Scanner(System.in);

        Companions = new Person[partySize];

        System.out.println("Please enter your wagon leader's name.");


        Companions[0] = this;
        Companions[0].Name = scanner.nextLine();

        for(int i = 1; i < Companions.length; i++)
        {
            System.out.println("Enter the name of wagon member #" + (i + 1));
            Companions[i] = new Person();
            Companions[i].Name = scanner.nextLine();
        }

        System.out.println("\nYour wagon leader's name is " + Companions[0].Name);
    }

    public void PrintDescription()
    {
        System.out.println("You are a " + Profession + "." + " You have $" +Money + " to spend at the general store.");
        System.out.println("You enter the general store and purchase supplies: ");

        System.out.println(Oxen + " oxen");
        System.out.println(Food + " pounds of food");

    }

    public String GetActiveCompanions()
    {
        String members = "";
        for(int i = 1 ; i < Companions.length; i++)
        {
            if(Companions[i].Alive) {
                members += " " + Companions[i].Name;
            }
        }
        return members;
    }

}
class Farmer extends Person
{
    public Farmer()
    {
        Profession = "Farmer";
        Money = 400;
        Oxen = 2;
        Food = 200;

        PrintDescription();
    }
}

class Banker extends Person
{
    public Banker()
    {
        Profession = "Banker";
        Money = 1600;
        Oxen = 6;
        Food = 600;

        PrintDescription();
    }
}
class Carpenter extends Person
{
    public Carpenter()
    {
        Profession = "Carpenter";
        Money = 800;
        Oxen = 4;
        Food = 400;

        PrintDescription();
    }


}

class Trail
{


    public String[] Locations;
    public int CurrentStop = 0;
    public int TotalDays = 0;

    public Trail()
    {
        Locations = new String[]
        {
                "Independence, MO",
                "Chimney Rock",
                "Fort Bridger",
                "Soda Springs",
                "Oregon City"
        };



    }


    public void TakeAChance(Person person)
    {
        Random random = new Random();

        int chance = random.nextInt(4);
        if(chance == 0) // loose food
        {
            int foundFood =GenerateRandomNumber(10, 100);
            if(random.nextInt(4) % 2 == 0)
            {
                person.Food -= foundFood;
                if(person.Food < 0)
                {
                    System.out.println("You run out of food. ");
                    KillAPerson(person);

                    person.Food = 0;
                }
            }
            else
            {
                int newAmmuntofFood = person.Food +foundFood;

                if(newAmmuntofFood > 600) {
                    person.Food = 600;
                }
                else {
                    person.Food += foundFood;
                }

                System.out.println("You found " + foundFood + " food! Just on the ground. Wow!");

            }

        }
        if(chance == 1) // find oxen
        {
            if(random.nextInt(4) % 2 == 0)
            {
                int foundOxen = GenerateRandomNumber(1, 3);
                System.out.println("You find " + foundOxen + " friendly oxen.");
                person.Oxen += foundOxen;
            }
            else
            {
                if(person.Oxen > 0) {
                    person.Oxen -= GenerateRandomNumber(1, 3);
                    System.out.println("You lost some Oxen. How sad. ");
                }
                else {
                    System.out.println("You think about your pet oxen.");
                }

                if(person.Oxen < 0)
                {
                    person.Oxen = 0;
                }
            }
        }
        if(chance == 2) // find money
        {
            int moneyFound = GenerateRandomNumber(10, 50);
            if(random.nextInt(4) % 2 == 0)
            {
                if(person.Money > 0)
                {
                    person.Money -= moneyFound;
                    System.out.println("You were robbed... sorry about that. (You lost $" + moneyFound + ")");
                }
                else
                {
                    System.out.println("You've gone bankrupt!");
                    person.Money = 0;
                }
            }
            else
            {
                person.Money += moneyFound;
                System.out.println("You found $" + moneyFound + " just laying on the ground... interesting.");
            }

            System.out.println("You currently have $" + person.Money);

        }
        if(chance == 3) // kill a person
        {
            KillAPerson(person);
        }
    }

    public void KillAPerson(Person person)
    {
        Random random = new Random();

        int luckyPerson = GenerateRandomNumber(1, person.Companions.length);
        if(person.Companions[luckyPerson].Alive)
        {
            person.Companions[luckyPerson].Alive = false;

            if(random.nextInt( 4) % 2 == 0)
            {
                System.out.println(person.Companions[luckyPerson].Name + " died of dysentery");
            }
            else
            {
                System.out.println(person.Companions[luckyPerson].Name + " died of cholera");
            }
        }
        else {
            System.out.println("You wasted time.");
        }
    }


    public void NextStop(Person person)
    {
        System.out.println("\n");
        PrintStats(person);


        CurrentStop++;
        CurrentStop = CurrentStop % 5;

        // move random amount of days no more than 10
        // party consumes 25lbs per day

        int daysToTravel = GenerateRandomNumber(1, 10);
        TotalDays += daysToTravel;
        int foodConsumed = 25 * daysToTravel;

        person.Food -= foodConsumed;
        if(person.Food < 0)
        {
            KillAPerson(person);
            person.Food = 0;
        }

        System.out.println("You travel " + daysToTravel + " days to reach the next stop.");
        System.out.println("Your party consumes " + foodConsumed + "lbs of food along the way. You have " + person.Food + "lbs. of food left.");

        System.out.println("\n");
    }

    public void PrintStats(Person person)
    {
        System.out.println("Round Stats:");
        System.out.println("Number of days on the Trail: " + TotalDays);
        System.out.println("Amount of Food in the Wagon " + person.Food);
        System.out.println("Number of Oxen: " + person.Oxen);
        System.out.println("Amount of Money: " + person.Money);
        System.out.println("Last stop visited: " + Locations[CurrentStop]);

        System.out.println("Active Companions: " + person.GetActiveCompanions());

    }

    public void Hunt(Person person)
    {
        int foodHunted = GenerateRandomNumber(1, 200);

        System.out.println("You go hunting and collect " + foodHunted + " lbs. of food");
        int currentFood = person.Food + foodHunted;

        if(currentFood > 600)
        {
            System.out.println("You can only fit 600lbs of food in your wagon.");
            person.Food = 600;
        }
        else
        {
            person.Food += foodHunted;
        }

        System.out.println("You now have " + person.Food + "lbs of food in the wagon.");

    }

    public int GenerateRandomNumber(int min, int max)
    {
        Random random = new Random();
        int randomNumber_int = random.nextInt(max - 1 ) + min;

        return randomNumber_int;
    }

    public void DisplayChoices(Person person)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose from the following options: ");
        System.out.println("1.   Continue to next stop. ");
        System.out.println("2.   Hunt for food.");
        System.out.println("3.   Take your chances");

        System.out.println("");

        int choice = scanner.nextInt();

        if (choice == 1)
        {
            NextStop(person);
        }
        else if (choice == 2)
        {
            Hunt(person);
        }
        else if (choice == 3)
        {
            TakeAChance(person);
        }
        else
        {
            System.out.println("Incorrect choice. Please try again.");
            DisplayChoices(person);
        }
    }
}

