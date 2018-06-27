/*
 * foxtrot_mid_task_1.c
 *
 *  Created on: Mar 20, 2018
 *      Author: masum
 *  atmega328p driving a seven segment common cathode display. seven segment A-G is connected to portd 0-6 in order
 *  		G F E D C B A
 *   0 -> 	0 1 1 1 1 1 1
 *   1 ->	0 0 0 0 1 1 0
 *   2 ->	1 0 1 1 0 1 1
 *   3 ->	1 0 0 1 1 1 1
 *   4 ->	1 1 0 0 1 1 0
 *   5 ->	1 1 0 1 1 0 1
 *   6 ->	1 1 1 1 1 0 1
 *	 7 ->	0 0 0 0 1 1 1
 *	 8 ->	1 1 1 1 1 1 1
 *	 9 ->	1 1 0 1 1 1 1
 *	 A ->	1 1 1 0 1 1 1
 *	 b ->	1 1 1 1 1 0 0
 *	 C ->	0 1 1 1 0 0 1
 *   d ->	1 0 1 1 1 1 0
 *   E ->	1 1 1 1 0 0 1
 *   F ->	1 1 1 0 0 0 1
 *
 *      _A
 *	  F|_|B
 *	  E|_|C
 *      D
 */

#include <avr/io.h>
#include <stdio.h>
#include <avr/delay.h>

#define F_CPU 16000000UL /**< Clock speed for delay functions. */



#define FOSC 16000000 /**< Clock speed for UBRR calculation. refer page 179 of 328p datasheet. */
#define BAUD 9600 /**< Baud Rate in bps. refer page 179 of 328p datasheet. */
#define MYUBRR FOSC/16/BAUD-1 /**< UBRR = (F_CPU/(16*Baud))-1 for asynch USART page 179 328p datasheet. Baud rate 9600bps, assuming	16MHz clock UBRR0 becomes 0x0067*/

/**
 * @brief Initialize USART for 8 bit data transmit no parity and 1 stop bit.
 *
 *@details This is a code snippet from datasheet page 182
 *
 * @param ubrr The UBRR value calculated in macro MYUBRR
 * @see MYUBRR
*/

const uint8_t segment_look_up[] ={
		0b00111111,//0
		0b00000110,//1
		0b01011011,//2
		0b01001111,//3
		0b01100110,//4
		0b01101101,//5
		0b01111101,//6
		0b00000111,//7
		0b01111111,//8
		0b01101111,//9
		0b01110111,//A
		0b01111100,//b
		0b00111001,//C
		0b01011110,//d
		0b01111001,//E
		0b01110001 //F
};

void USART_init(unsigned int ubrr)
{

	UCSR0C = (0<<USBS0)|(3<<UCSZ00); /// Step 1. Set UCSR0C in Asynchronous mode, no parity, 1 stop bit, 8 data bits
	UCSR0A = 0b00000000;/// Step 2. Set UCSR0A in Normal speed, disable multi-proc

	UBRR0H = (unsigned char)(ubrr>>8);/// Step 3. Load ubrr into UBRR0H and UBRR0L
	UBRR0L = (unsigned char)ubrr;


	UCSR0B = 0b00011000;/// Step 4. Enable Tx Rx and disable interrupt in UCSR0B
}

/**
 * @brief Send 8bit data.
 *
 *@details This is a code snippet from datasheet page 184
 *
 * @param data The 8 bit data to be sent
*/

/**
 * @brief Send 8bit data.
 *
 *@details This is a code snippet from datasheet page 184
 *
 * @param data The 8 bit data to be sent
*/

int USART_send(char c, FILE *stream)
{

	while ( !( UCSR0A & (1<<UDRE0)) )/// Step 1.  Wait until UDRE0 flag is high. Busy Waitinig
	{;}

	UDR0 = c; /// Step 2. Write char to UDR0 for transmission
}
/**
 * @brief Receive 8bit sata.
 *
 *@details This is a code snippet from datasheet page 187
 *
 * @return Returns received data from UDR0
*/
int USART_receive(FILE *stream )
{

	while ( !(UCSR0A & (1<<RXC0)) )/// Step 1. Wait for Receive Complete Flag is high. Busy waiting
		;

	return UDR0;/// Step 2. Get and return received data from buffer
}

/**
 * @brief Program entry point.
 *
 *@details This is a code snippet from datasheet page 187.
 *Initialize the usart communication.
 *Then in the while loop first wait for the data and then echo it back.
 *
*/

int main()
{
	DDRD = 0b01111111;
	//Following will have same effect
	//DDRD |= (1<<DDD6)|(1<<DDD5)|(1<<DDD4)|(1<<DDD3)|(1<<DDD2)|(1<<DDD1)|(1<<DDD0);
	//DDRD |= _BV(DDD6)|_BV(DDD5)|_BV(DDD4)|_BV(DDD3)|_BV(DDD2)|_BV(DDD1)|_BV(DDD0);
	PORTD = 0x00;


	unsigned char rec; // variable to store received data
	USART_init(MYUBRR);
	stdout = fdevopen(USART_send, NULL);
	stdin = fdevopen(NULL, USART_receive);
	while(1)
	{
		printf("Enter 1st number:\t");
		scanf("%c",&rec);
		printf("Enter operator:\t");
		scanf("%c",&rec);
		printf("Enter 2nd number:\t");
		scanf("%c",&rec);
		printf(" typed value %c\n",rec);

	}

}

