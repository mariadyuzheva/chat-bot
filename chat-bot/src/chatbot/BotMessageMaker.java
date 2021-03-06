package chatbot;

import java.util.ArrayList;
import java.util.HashMap;

import chatbot.ChatBot.ReplyType;
import chatbot.GibbetGame.TurnResult;

public class BotMessageMaker {
	
	public BotMessage getMessage(BotReply reply) {
		String photoName = null;
		var textFragments = new ArrayList<String>();
		
		if (reply.turnResult != null) {
			photoName = Integer.toString(reply.wrongGuesses + 1) + ".jpg";
			textFragments.add(getTurnResultReplyFragment(reply));
		}
	
		for (var i = 0; i < reply.replyTypes.size(); i++) {
			textFragments.add(getReplyFragment(reply.replyTypes.get(i), reply));
		}
		return new BotMessage(String.join(
				"\n", textFragments), photoName, getAvailableOperations(reply), reply.photo,
				reply.replyTypes.contains(ReplyType.start),
				reply.replyTypes.contains(ReplyType.end), 
				reply.timer,
				reply.task,
				reply.delay);
	}

	private String getTurnResultReplyFragment(BotReply reply) {
		if (reply.turnResult == TurnResult.rightGuess)
			return "You are right!";
		else if (reply.turnResult == TurnResult.wrongGuess)
			return "This is a wrong guess!";
		else
			throw new RuntimeException(reply.turnResult.toString());
	}

	private String getReplyFragment(ReplyType replyType, BotReply reply) {
		switch(replyType) {
			case help:
				return "This is a Gibbet-game bot.\n"
						+ "/start - to start a new game.\n"
						+ "/restart - to restart a game.\n"
						+ "/start hard - to start a new game on a hard level.\n"
						+ "/restart hard - to restart a game on a hard level.\n"
						+ "/end - to end the current game.\n"
						+ "/show - to show the word, which you guess.\n"
						+ "/help - to see this message.";
			case start:
				return "New game started. Guess one letter!";
			case win:
				return "You win!";
			case loss:
				return "You lose!";
			case end:
				return "Game over!";
			case repeatedGuess:
				return "You have already guessed this letter!";
			case show:
				return reply.guessedWord;
			case endNotStartedGame:
				return "You should start game to end it!";
			case closedLevel:
				return "This level is closed!";
			default:
				return "I don't understand!";
		}
	}

	private HashMap<String, String> getAvailableOperations(BotReply reply) {
		var availableOperations = new HashMap<String, String>();
		
		for (String operation : reply.availableOperations) 
			switch(operation) {
				case "start":
					availableOperations.put("/start", "start");
					break;
				case "start hard":
					availableOperations.put("/start hard", "start hard");
					break;
				case "restart":
					availableOperations.put("/start", "restart");
					break;
				case "restart hard":
					availableOperations.put("/start hard", "restart hard");
					break;
				case "end":
					availableOperations.put("/end", "end");
					break;
				case "show":
					availableOperations.put("/show", "show");
					break;
				case "help":
					availableOperations.put("/help", "help");
					break;
				case "hint":
					availableOperations.put("/hint", "hint");
					break;
			}
		return availableOperations;
	}
}
