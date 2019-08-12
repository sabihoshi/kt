package dev.kao.hizamakura

// Will return T or null based on T.parseOrNull()
fun <T> promptOrNull(prompt: String, parseOrNull: (String) -> T?): T? {
    print("$prompt ")
    return parseOrNull(readLine()!!)
}

// Will keep prompting until T is not null
fun <T> promptNotNull(prompt: String, parseOrNull: (String) -> T?): T {
    var result: T?;
    do {
        result = promptOrNull(prompt, parseOrNull);
    } while (result == null)
    return result;
}

// Prompts the user and returns T
fun <T> prompt(prompt: String, parse: (String) -> T): T {
    print("$prompt ")
    return parse(readLine()!!)
}