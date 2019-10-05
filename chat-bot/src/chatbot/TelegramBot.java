package chatbot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.HashMap;
import java.util.Random;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot{
	
	private HashMap<String, ChatBot> chatBots;
	private BotMessage botMessage;
	
	public TelegramBot() {
		chatBots = new HashMap<String, ChatBot>();
		botMessage = new BotMessage();
	}

	public static void main(String[] args) {
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

		try {
			telegramBotsApi.registerBot(new TelegramBot());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpdateReceived(Update update) {
		var message = update.getMessage().getText();
		var id = update.getMessage().getChatId().toString();
		if (!chatBots.containsKey(id)) {
			chatBots.put(id, new ChatBot(new GibbetGameFactory(new Random())));
		}
		var replies = chatBots.get(id).reply(message);
		var answer = "";
		for (var i = 0; i < replies.size(); i++) {
			answer += "\n" + botMessage.getMessage(replies.get(i));
		}
		sendMsg(update.getMessage().getChatId().toString(), answer);
	}

	private void sendMsg(String chatId, String answer) {
		SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(answer);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
        	e.printStackTrace();
        }
	}

	@Override
	public String getBotUsername() { 
		return "blablabla";
	}

	@Override
	public String getBotToken() { 
		return "blablabla"; 
	}
}