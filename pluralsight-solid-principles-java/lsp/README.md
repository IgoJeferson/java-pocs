# Applying Liskov Substitution Principle (LSP)

- What is Applying Liskov Substitution Principle?
- Violations of the Liskov Substitution Principles
- Fixing incorrect relationships between types

Liskov Substitution Principle: If S is a subtype of T, then objects of type T in a program may be replaced with objects of type S without modifying the functionality of the program.

Other good definition is: Any object of a type must be substitutable by objects of a derived typed without altering the correctness of that program.

Two ways to refactor code to LSP

- Eliminate incorrect relations between objects
- Use "Tell, don't ask!" principle to eliminate type checking and casting.

Apply the LSP in a Proactive Way

- Make sure that a derived type can be substitute its base type completely
- Keep base classes small and focused
- Keep interfaces lean

LSP Summary:

- Don't think relationships in terms of "IS A"
- Empty methods, type checking and hardened preconditions are signs that you are violating the LSP
- LSP also applies for interfaces, not just for class inheritance

> Real life categories do not always map to OOP relationships.