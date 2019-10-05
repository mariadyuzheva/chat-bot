package chatbot;

import java.util.ArrayList;

public class ChatBot {

	private GibbetGameFactory gameFactory;
	private GibbetGame game;
	
	public ChatBot(GibbetGameFactory gameFactory) {
		super();
		this.gameFactory = gameFactory;
	}

	private GibbetGame.gameState checkWinOrLoss() {
		if (game.isWin())
		{
			var result = GibbetGame.gameState.win;
			game = null;
			return result;
		}
		if (game.isLoss())
		{
			var result = GibbetGame.gameState.loss;
			game = null;
			return result;
		}
		return null;
	}
	
	public ArrayList<BotReply> reply(String message){
		var result = new ArrayList<BotReply>();
		var variables = new ArrayList<String>();
		switch (message) {
			case "/start":
				game = gameFactory.createNew();
				result.add(new BotReply(new ArrayList<String>(), GibbetGame.gameState.start));
				variables.add(game.showWord());
				result.add(new BotReply(variables, GibbetGame.gameState.showWord));
				return result;
			case "/help":
				result.add(new BotReply(new ArrayList<String>(), GibbetGame.gameState.help));
				return result;
			case "/end":
				game = null;
				result.add(new BotReply(new ArrayList<String>(), GibbetGame.gameState.end));
				return result;
			case "/show":
				if (game != null) {
					variables.add(game.showWord());
					result.add(new BotReply(variables, GibbetGame.gameState.showWord));
					return result;
				}
				result.add(new BotReply(new ArrayList<String>(), GibbetGame.gameState.help));
				return result;
			default:
				if (game != null) {
					if (message.matches("[a-z]{1}"))
					{
						var answer = game.checkLetter(message.charAt(0));
						result.add(new BotReply(new ArrayList<String>(), answer));
						variables.add(game.showWord());
						result.add(new BotReply(variables, GibbetGame.gameState.showWord));
						var winOrLoss = checkWinOrLoss();
						if (winOrLoss != null)
							result.add(new BotReply(new ArrayList<String>(), winOrLoss));
						return result;
					}
					result.add(new BotReply(new ArrayList<String>(), GibbetGame.gameState.strangeGuess));
					return result;
				}
				result.add(new BotReply(new ArrayList<String>(), GibbetGame.gameState.help));
				return result;
		}
	}
}
