#include <stdio.h>
#include "spimcore.h"


//additional function to make ALU work//
void sltFunct(unsigned A, unsigned B, unsigned *tempALUResult);


/* ALU */
/* 10 Points */
void ALU(unsigned A,unsigned B,char ALUControl,unsigned *ALUresult,char *Zero)
{
  unsigned tempALUResult; 
    if(ALUControl==0x0) {
        
            tempALUResult = A + B;
      }
     if(ALUControl==0x1) {
            tempALUResult = A - B;
     }
     if(ALUControl==0x2) {
            sltFunct(A, B, &tempALUResult);
       }
      if(ALUControl==0x3) {
            if (A < B){
                tempALUResult = 0x1;
            }
            else{
                tempALUResult = 0x0; 
           }
      }
      if(ALUControl==0x4) {
            tempALUResult = A & B;
      }
      if(ALUControl==0x5) {
            tempALUResult = A | B;
      }
      if(ALUControl==0x6) {
            tempALUResult = B << 16;
      }
      if(ALUControl==0x7) {
            tempALUResult = ~A;
      }

      if(tempALUResult== 0x0){
        *Zero = 0x1;
      }
      else{
        *Zero = 0x0;
      }

      *ALUresult= tempALUResult;
}

/* instruction fetch */
/* 10 Points */
int instruction_fetch(unsigned PC,unsigned *Mem,unsigned *instruction)
{
  if(PC % 4 == 0)
    *instruction = Mem[PC >> 2];
  else
    return 1;

  return 0;

}


/* instruction partition */
/* 10 Points */
void instruction_partition(unsigned instruction, unsigned *op, unsigned *r1,unsigned *r2, unsigned *r3, unsigned *funct, unsigned *offset, unsigned *jsec)
{

  *op = (instruction & 0xfc000000); //masking for bit [31-26]
  *r1 = (instruction & 0x03e00000) >> 21; //masking for bit [25-21] for register 1
  *r2 = (instruction & 0x001f0000) >> 16; //masking for bit [20-16] for register 2
  *r3 = (instruction & 0x0000f800) >> 11; //masking for bit [15-11] for register 3
  *funct = instruction & 0x0000003f;  //masking for bit [5-0] for function
  *offset = instruction & 0x0000ffff; //masking for bit [15-0] for offset
  *jsec = instruction & 0x03ffffff; //masking for bit [25-0] for jump

}



/* instruction decode */
/* 15 Points */
int instruction_decode(unsigned op,struct_controls *controls)
{
        if (op == 0x000000000) { 
            controls->RegDst = 0x1; 
            controls->Jump = 0x0; 
            controls->Branch = 0x0; 
            controls->MemRead = 0x0; 
            controls->MemtoReg = 0x0; 
            controls->ALUOp = 0x7; 
            controls->MemWrite = 0x0; 
            controls->ALUSrc = 0x0; 
            controls->RegWrite = 0x1;
        }
        else if (op == 0x20000000) {
            controls->RegDst = 0x0; 
            controls->Jump = 0x0; 
            controls->Branch = 0x0; 
            controls->MemRead = 0x0; 
            controls->MemtoReg = 0x0; 
            controls->ALUOp = 0x0; 
            controls->MemWrite = 0x0; 
            controls->ALUSrc = 0x1; 
            controls->RegWrite = 0x1; 
        }
        else if (op== 0x8c000000) {
            controls->RegDst = 0x0; 
            controls->Jump = 0x0; 
            controls->Branch = 0x0; 
            controls->MemRead = 0x1; 
            controls->MemtoReg = 0x1; 
            controls->ALUOp = 0x0; 
            controls->MemWrite = 0x0; 
            controls->ALUSrc = 0x1; 
            controls->RegWrite = 0x1; 
        }
        else if (op == 0xac000000){ 
            controls->RegDst = 0x0; 
            controls->Jump = 0x0; 
            controls->Branch = 0x0; 
            controls->MemRead = 0x0; 
            controls->MemtoReg = 0x0; 
            controls->ALUOp = 0x0; 
            controls->MemWrite = 0x1; 
            controls->ALUSrc = 0x1; 
            controls->RegWrite = 0x0; 
        }
        else if (op == 0x3c000000) { 
            controls->RegDst = 0x0; 
            controls->Jump = 0x0; 
            controls->Branch = 0x0; 
            controls->MemRead = 0x0; 
            controls->MemtoReg = 0x0;
            controls->ALUOp = 0x6; 
            controls->MemWrite = 0x0; 
            controls->ALUSrc = 0x1;
            controls->RegWrite = 0x1; 
        }
        else if(op== 0x28000000) {
            controls->RegDst = 0x0; 
            controls->Jump = 0x0;
            controls->Branch = 0x0; 
            controls->MemRead = 0x0; 
            controls->MemtoReg = 0x0;
            controls->ALUOp = 0x2; 
            controls->MemWrite = 0x0;
            controls->ALUSrc = 0x1; 
            controls->RegWrite = 0x1; 
        }
        else if(op== 0x2c000000){
            controls->RegDst = 0x0; 
            controls->Jump = 0x0; 
            controls->Branch = 0x0; 
            controls->MemRead = 0x0; 
            controls->MemtoReg = 0x0; 
            controls->ALUOp = 0x3; 
            controls->MemWrite = 0x0; 
            controls->ALUSrc = 0x1; 
            controls->RegWrite = 0x1; 
        }
        else if (op == 0x10000000){
            controls->RegDst = 0x0; 
            controls->Jump = 0x0; 
            controls->Branch = 0x1; 
            controls->MemRead = 0x0; 
            controls->MemtoReg = 0x0;
            controls->ALUOp = 0x1; 
            controls->MemWrite = 0x0; 
            controls->ALUSrc = 0x0; 
            controls->RegWrite = 0x0; 
        }
        else if (op ==  0x08000000) { 
            controls->RegDst = 0x0; 
            controls->Jump = 0x1; 
            controls->Branch = 0x0; 
            controls->MemRead = 0x0; 
            controls->MemtoReg = 0x0; 
            controls->ALUOp = 0x0; 
            controls->MemWrite = 0x0; 
            controls->ALUSrc = 0x0; 
            controls->RegWrite = 0x0; 
        }
        else 
            return 1; 

    return 0; 
}

/* Read Register */
/* 5 Points */
void read_register(unsigned r1,unsigned r2,unsigned *Reg,unsigned *data1,unsigned *data2)
{
  *data1 = Reg[r1], *data2 = Reg[r2];

}


/* Sign Extend */
/* 10 Points */
void sign_extend(unsigned offset,unsigned *extended_value)
{
  //check if offset is negative or positive
	if((offset >> 15) == 1)
		*extended_value = offset | 0xffff0000;
	else
		*extended_value = offset & 0x0000ffff;

}

/* ALU operations */
/* 10 Points */
int ALU_operations(unsigned data1,unsigned data2,unsigned extended_value,unsigned funct,char ALUOp,char ALUSrc,unsigned *ALUresult,char *Zero)
{
    //works!
        if(ALUOp == 0x0) {
            if (ALUSrc == 1)
                ALU(data1, extended_value, ALUOp, ALUresult, Zero);
            
            else
                ALU(data1, data2, ALUOp, ALUresult, Zero);
            
        }
        else if(ALUOp == 0x1) {
            if (ALUSrc == 1)
                ALU(data1, extended_value, ALUOp, ALUresult, Zero);
            
            else
                ALU(data1, data2, ALUOp, ALUresult, Zero);
        }
        else if(ALUOp == 0x2) {
            if (ALUSrc == 1)
                ALU(data1, extended_value, ALUOp, ALUresult, Zero);
    
            else
                ALU(data1, data2, ALUOp, ALUresult, Zero);
        }
        else if(ALUOp== 0x3) {
            if (ALUSrc == 1)
                ALU(data1, extended_value, ALUOp, ALUresult, Zero);
            
            else 
                ALU(data1, data2, ALUOp, ALUresult, Zero);
        }
        else if(ALUOp== 0x4) {
            if (ALUSrc == 1)
                ALU(data1, extended_value, ALUOp, ALUresult, Zero);
            
            else 
                ALU(data1, data2, ALUOp, ALUresult, Zero);
        } 
        else if(ALUOp== 0x5) {
            if (ALUSrc == 1)
                ALU(data1, extended_value, ALUOp, ALUresult, Zero);
            
            else
                ALU(data1, data2, ALUOp, ALUresult, Zero);
        }
        else if(ALUOp== 0x6) {
            if (ALUSrc == 1)
                ALU(data1, extended_value, ALUOp, ALUresult, Zero);
            
            else
                ALU(data1, data2, ALUOp, ALUresult, Zero);
        }
        else if(ALUOp== 0x7){
            if (funct == 0x20)
                ALU(data1, data2, 0, ALUresult, Zero);
            else if (funct == 0x22)
                ALU(data1, data2, 1, ALUresult, Zero);
            else if (funct == 0x24)
                ALU(data1, data2, 4, ALUresult, Zero);
            else if (funct == 0x25)
                ALU(data1, data2, 5, ALUresult, Zero);
            else if (funct == 0x2A) 
                ALU(data1, data2, 2, ALUresult, Zero);
            else if (funct == 0x2B) // function is 0b101011 so send 3 to alu
                ALU(data1, data2, 3, ALUresult, Zero);
             else
                return 1; //halt function is not recognized
        }
        else
            return 1; 

        return 0;
}

/* Read / Write Memory */
/* 10 Points */
int rw_memory(unsigned ALUresult,unsigned data2,char MemWrite,char MemRead,unsigned *memdata,unsigned *Mem)
{
  
    if ((MemWrite == 1 || MemRead == 1) && ((ALUresult % 4) != 0))
		  return 1;
	
	  if (MemWrite == 1)
	  	Mem[ALUresult >> 2] = data2;
	
	  if (MemRead == 1)
		  *memdata = Mem[ALUresult >> 2];

    return 0;
}


/* Write Register */
/* 10 Points */
void write_register(unsigned r2,unsigned r3,unsigned memdata,unsigned ALUresult,char RegWrite,char RegDst,char MemtoReg,unsigned *Reg)
{
  
  //writes to register 2
  if(RegDst == 0x0 && RegWrite == 0x1){
    if(MemtoReg == 0x0) //cases for stli, sltui, lui, addi...
    {
      if(r2 == 0) //cant write here
        return;
        
      Reg[r2] = ALUresult;
    }
    else if(MemtoReg == 0x1) //case for loading word
    {
      if(r2 == 0) //cant write here
        return;
        
      Reg[r2] = memdata;
    } 
  }
  //rtype to register 3
  if(RegDst == 0x1 && RegWrite == 0x1 && MemtoReg == 0x0){ 
    if(r3 == 0) // cant write here
      return;
    
    Reg[r3] = ALUresult;
  }


}

/* PC update */
/* 10 Points */
void PC_update(unsigned jsec,unsigned extended_value,char Branch,char Jump,char Zero,unsigned *PC)
{
  //works!
  unsigned tempPCupdate = *PC + 4;

  if (Jump == 0x0)
  {
    if(Branch == 0x0)
      *PC = tempPCupdate;
    

    else if(Branch == 0x1)
      if (Zero == 0x1)
       *PC = (extended_value * 4) + tempPCupdate;
  }
  else if (Jump == 0x1)   // jump statment is never reached, adjusted code
    if(Branch == 0x0)
      *PC = (tempPCupdate & 0xF0000000) | (jsec * 4); //we are shifting jsec 2 bits to the left so multiply by 2^2 or 4
}

///////////////////////////////////////////////
//////////////////additional function/////////

void sltFunct(unsigned A, unsigned B, unsigned *tempALUResult){

  unsigned bitmask = 0x10000000;
  unsigned maskedA = A & bitmask, maskedB = B & bitmask;

//maskedA pos
  if (maskedA == 0x0000000){
    if(maskedB == 0x0000000){//both maskedA&maskedB pos
        if (A>B){
          *tempALUResult = 0x0;
        }
        else{
          *tempALUResult = 0x1;
        }
    }
    //maskedB positive
    else{
          *tempALUResult = 0x0;
    }
  }
//maskedA is negative
  if (maskedA == 0x1000000){
    if(maskedB == 0x1000000){ 
      //both maskedA&maskedB are neg
      A = ~A + 1;
      B = ~B + 1;

      if(A>B){
        *tempALUResult = 0x0;
      }
      else{
        *tempALUResult = 0x1;
      }
    }
    //maskedA neg &maskedB positive
    else{
      *tempALUResult = 0x1;

    }

   }
 }