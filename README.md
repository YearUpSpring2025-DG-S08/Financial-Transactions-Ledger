# Financial-Transactions-Ledger
YearUp

This project is a financial tracker application that reads and writes accounting information within a ledger. The project allows a user to add a deposit/payment to the ledger, displays entries by deposit/payment,
dates, monthToDate, previousMonthToDate, year, previousYear, vendor, and customSearch. 

![image](https://github.com/user-attachments/assets/c9d0bdaa-2ea1-40a2-ace6-3a1893b7b5b7)
![image](https://github.com/user-attachments/assets/08f14f0e-9c6d-4d2c-ba26-eda9da458043)
![image](https://github.com/user-attachments/assets/1e8e1663-7892-49ff-8eed-a164bb583e76)
![image](https://github.com/user-attachments/assets/b05ad523-bcd9-4436-8f89-350917333af8)
![image](https://github.com/user-attachments/assets/3b259c7c-6155-4110-8a13-36c25c829cc4) (ran into an error and had to re-run code)
![image](https://github.com/user-attachments/assets/7defd44a-af99-4f6d-ba33-09314895547a)
![image](https://github.com/user-attachments/assets/7d1deca5-cafa-4941-bde0-131df1560699)
![image](https://github.com/user-attachments/assets/ed4cdfbc-d809-4497-a769-580d97f83582)
![image](https://github.com/user-attachments/assets/4f8469d7-7206-46e6-b6f7-b6c6ae6938c6)
![image](https://github.com/user-attachments/assets/8013e9eb-b132-4353-81b3-ceabd690295b)


![image](https://github.com/user-attachments/assets/d57eb8e5-c950-46ba-b054-e8199c70e6a3)
The code written within the custom search method was the most intense portion of the project. Once starting this portion, I had initially wanted to have the user input the data within one single input and I would parse our their response. However, that proved too difficult and so I had to face the dilemma of creating multiple print lines and scanner lines for the input. As well as attempting to filter each input, whether or not the user actually inputted data. 

![image](https://github.com/user-attachments/assets/1aa04623-5504-403c-abc2-90087a39dead)
Another piece of code that I found most interesting was completing the addTransaction method. Once I realized that the deposit and payment functions were the same, it made the decision easier to create its own method that would handle both transacations. The most exciting part was discovering how to add the current time and date to a user's entry without them having to input it and risk further user area during the entry. Utilizing the Math.abs() method to ensure that if the user was doing a payment that the amount would populate as a negative number was also a fun task.
