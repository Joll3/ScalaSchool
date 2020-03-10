
# This assignment asks you to implement some basic word operations
# with assembly language.

# Namely, your solution should implement all of the following:
# 1) Reverse the order of bits in $0 and store the result in $5
#    (that is, the bit in position 0 goes to position 15, position 1 goes
#    to position 14, position 2 goes to position 13, ..., and position 15
#    goes to position 0; the contents of $0 must remain unchanged);
# 2) Count the number of 1-bits in $0 and store the result in $6; and
# 3) Rotate the bits in $0 by one position to the left and store 
#    the result in $7. (That is, bit in position 0 goes to position 1, 
#    position 1 goes to 2, position 2 goes to 3, ..., position 14 goes 
#    to position 15, and position 15 goes to position 0.)

# Here is some wrapper code to test your solution:
        
        mov     $0, 62361       # load test input to $0
                
# Your solution starts here ...
# ------------------------------------------
        
        mov $5, 0
        mov $2, $0 
        mov $3, 0
        mov $6, 0
        mov $7, $0
        mov $4, 0

        @loop:
        		and $1, $2, 1
        		cmp $1, 1
        		beq >addone
        		
        	@lisatty:	
        		add $5, $5, $1
        		
        		lsr $2, $2, 1
        		cmp $3, 15
        		add $3, $3, 1
        		beq >osa1
        		lsl $5, $5, 1
        		
        		jmp >loop
        	
        	@addone:
        		add $6, $6, 1
        		jmp >lisatty
        		
        	@osa1:
			lsl $7, $7, 1
        		add $7, $7, $1
# ------------------------------------------
# ... and ends here 

        hlt                     # the processor stops here

# (at halt we should have 39375 in $5, 10 in $6, and 59187 in $7)

