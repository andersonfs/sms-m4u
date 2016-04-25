Para executar a aplicação:

1 - Baixar e construir com o maven a dependência swagger-java-client:
	1.1 - Após efetuar o download acessar a pasta através do console;
	1.2 - Executar os seguintes comandos:
		1.2.1 - mvn clean
		1.2.2 - mvn compile
		1.2.3 - mvn package
		1.2.4 - mvn install
2 - Executando o sms-m4u através de um projeto como uma dependência:
	2.1  Construir o sms-m4u com os seguintes comandos maven. Acessar a pasta do sms-m4u através do console e executar os seguintes comandos:
		2.2.1 - mvn clean
		2.2.2 - mvn compile
		2.2.3 - mvn package
		2.2.4 - mvn install
	2.2 - Inserir o jar do sms-m4u em um projeto;
	2.3 - Usar o método "sendMenssage()" da classe "SMSSingleClient" passandos os seguintes parâmetros:
		2.3.1 - textMessage = Mensagem do sms;
		2.3.2 - validity = Data de validade do sms;
		2.3.3 - originPhoneNumber = Número do telefone de origem;
		2.3.4 - destinationPhoneNumber =  Número do telefone de destino.
	2.4 - Os retornos possíveis são:
		2.4.1 - OK - Código =201, Mensagem = "SMS sent" - Mensagem enviada com sucesso;
		2.4.2 - NOT_FOUND - Código = 404, Mensagem = "Mobile User not found" - Usuário não encontrado;
		2.4.3 - VALIDATION_EXCEPTION - Código = 405, Mensagem = "Validation exception" - Algum parâmetro não informado;
		2.4.4 - INTERNAL_SERVER_ERROR - Código = 500, Mensagem = "Internal Server Error" - Algum erro interno ocorrido;
		2.4.5 - NO_RECOGNIZE - Código = 0, Mensagem = "Status not found." - Algum retorno da API não reconhecido.
3 - Executando pelo Eclipse:
	3.1 - Abrir o projeto no Eclipe;
	3.2 - Executar a classe de testes "SMSSingleClientTest" usando o JUnit.

Em caso de dúvidas entrar em contato com andersonfs@gmail.com.