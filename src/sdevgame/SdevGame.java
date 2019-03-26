/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdevgame;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author kanip
 */
public class SdevGame {

    private int currentGame = 0;
    private int humanPoints = 0;
    private int aiPoints = 0;
    private int[] humanExtraPoints = new int [100];
    private int[] aiExtraPoints = new int [100];
    private int[] currentRound = new int[100];
    private int[] humanLostRound = new int[100];
    private int[] humanWonRound = new int[100];
    private int[] evenPerGameHuman = new int [100];
    private int[] oddPerGameHuman = new int [100];
    private int[] evenPerGameAi = new int [100];
    private int[] oddPerGameAi = new int [100];
    private int[][] humanFingers = new int[100][5];
    private int[][] aiFingers = new int[100][5];
    private final int maxScore = 6;
    private String evenOdd = "";
    private String yesNo;

	Scanner input = new Scanner(System.in);
    //engin of a game
	public void process() {


		        for (int game = 0; game<100; game++) { //loop for games
		            this.initiation(); //reset of atributes
		            this.inputEO(); //even odd choice

		            for (int round=0; round<5; round++) { //loop for rounds
                        System.out.println("\nEnter number between 0 and 10: ");
                        currentRound[currentGame]++; //setting round
                        this.userInput(); //seting player fingers
                        this.aiInput(); //setting AI fingers
                        System.out.println("Your choice " + (humanFingers[currentGame][currentRound[currentGame]]) + ". AI choice: " + aiFingers[currentGame][currentRound[currentGame]] + ". The sum is: " + (humanFingers[currentGame][currentRound[currentGame]] + aiFingers[currentGame][currentRound[currentGame]]));
                        this.pointsCalculation(); //points calculations
                        if (humanPoints >= maxScore) { //checking if single game finish
                            System.out.println("You win! ");
                            this.singleGame(); //summary of single game
                            this.playAgain(); //yes no choice
                        }
                        if (aiPoints >= maxScore) { //checking if single game finish
                            System.out.println("You lost ");
                            this.singleGame(); //summary of single game
                            this.playAgain(); //yes no choice
                        }
                    }
		        }
	}

	//Game initiation - reset variables
    public void initiation() {
        currentRound[currentGame] = 0;
        humanPoints = 0;
        aiPoints = 0;
     }

	//even odd choice
    public void inputEO() {
        System.out.println("Enter odd numbers (1) or even (2): ");
        int value = input.nextInt();
        if (value == 2) {
           		evenOdd = "even";
        } else if (value == 1){
        		evenOdd = "odd";
        } else {
               this.inputEO(); //repeat choice for incorect input
        }
		System.out.println("You chose: " + evenOdd);

	}

	//player no of fingers
    public void userInput() {
        int num = input.nextInt();
            humanFingers[currentGame][currentRound[currentGame]] = num;
            if (num < 0 || num > 10) {
                System.out.println("Enter again ");
                this.userInput(); //repeat if incorect input
            }
    }

	//AI random choice for number of fingers
    public void aiInput(){
        Random rnd = new Random();
         int rd = rnd.nextInt(11);
         aiFingers[currentGame][currentRound[currentGame]] = rd;
    }

	// single game summary
    public void singleGame() {
        System.out.println("Game summary: ");
        System.out.println("\nYou chose: " + evenOdd + " numbers");
        System.out.println("Numbers chose by you: ");
        for (int youNum = 1; youNum < currentRound[currentGame] + 1; youNum++) { //loop to display each round human fingers
            System.out.println("Round " + youNum + ": " + humanFingers[currentGame][youNum]);
        }
        System.out.println("\nNumbers chose by AI: ");
        for (int aiNum = 1; aiNum < currentRound[currentGame] + 1; aiNum++) { //loop to display each round AI fingers
            System.out.println("Round " + aiNum + ": " + aiFingers[currentGame][aiNum]);
        }
    }

    //choice for another game
    public void playAgain(){
        System.out.print("\nWould you like to play another game? (y/n)");
        yesNo = input.next();

        if (yesNo.charAt(0) == 'n' || yesNo.charAt(0) == 'N') {
            this.gameSummary();
            System.exit(0); // exit if 'no'

        } else { //set a number of game and back to process
            currentGame++;
            this.process();
        }

    }



    public void pointsCalculation(){

        //calculate extra points
        if (humanFingers[currentGame][currentRound[currentGame]] > aiFingers[currentGame][currentRound[currentGame]]){
            humanPoints += 1;
            humanExtraPoints[currentGame] += 1;
            System.out.println("You get 1 extra point"); //when human won
        }
        if (humanFingers[currentGame][currentRound[currentGame]] < aiFingers[currentGame][currentRound[currentGame]]){
            aiPoints += 1;
            aiExtraPoints[currentGame] +=1;
            System.out.println("AI get 1 extra point"); //when AI won
        }
        if(humanFingers[currentGame][currentRound[currentGame]] == aiFingers[currentGame][currentRound[currentGame]]){
            aiPoints +=1;
            aiExtraPoints[currentGame] +=1;
            humanPoints +=1;
            humanExtraPoints[currentGame] +=1;
            System.out.println("You get 1 extra  point"); //when draw
            System.out.println("AI get 1 extra point");
        }

        //calculate round winner
        int sum = humanFingers[currentGame][currentRound[currentGame]] + aiFingers[currentGame][currentRound[currentGame]]; //sum of fingers
        if(sum%2 == 0 && evenOdd == "even"){
            humanPoints += 2; //human won round
            humanWonRound[currentGame] =+1;
            System.out.println("You get 2 points");
            System.out.println("You have " +humanPoints + " points");
            System.out.println("AI has " +aiPoints + " points");
        }
        if (sum%2 == 1 && evenOdd == "odd"){
            humanPoints += 2; //human won round
            humanWonRound[currentGame] =+1;
            System.out.println("You get 2 points");
            System.out.println("You have " +humanPoints + " points");
            System.out.println("AI has " +aiPoints + " points");
        }
        if (sum%2 == 1 && evenOdd == "even"){
            aiPoints += 2; //AI won round
            humanLostRound[currentGame] +=1;
            System.out.println("AI get 2 points");
            System.out.println("You have " +humanPoints + " points");
            System.out.println("AI has " +aiPoints + " points");
        }
        if (sum%2 == 0 && evenOdd == "odd"){
            aiPoints += 2; //AI won round
            humanLostRound[currentGame] +=1;
            System.out.println("AI get 2 points");
            System.out.println("You have " +humanPoints + " points");
            System.out.println("AI has " +aiPoints + " points");
        }

        //calculate how many odd/even numbers has been chosen by players
        if(humanFingers[currentGame][currentRound[currentGame]] %2 ==0) {
            evenPerGameHuman[currentGame] += 1;
        }
        if (humanFingers[currentGame][currentRound[currentGame]] %2 ==1) {
            oddPerGameHuman[currentGame] += 1;
        }
        if(aiFingers[currentGame][currentRound[currentGame]] %2 == 0){
            evenPerGameAi[currentGame] += 1;
        }
        if(aiFingers[currentGame][currentRound[currentGame]] %2 == 0){
            oddPerGameAi[currentGame] += 1;
        }
    }
	//games summary
    public void gameSummary(){
		System.out.println("Games summary: ");
		for (int i =0; i < currentGame +1; i++){
			System.out.println("Game number: " + (i+1));
			System.out.println("Round won: " + humanWonRound[i]);
			System.out.println("Round lost: " + humanLostRound[i]);
			System.out.println("Even chosen by you: " + evenPerGameHuman[i] + " times");
			System.out.println("Odd chosen by you: " + oddPerGameHuman[i] + " times");
			System.out.println("Even chosen by AI: " + evenPerGameAi[i] + " times");
			System.out.println("Odd chosen by AI: " + oddPerGameAi[i] + " times");
			System.out.println("Extra points received: " + humanExtraPoints[i]);
            System.out.print("\nThank you for playing \n");
            }
    }
}
    

