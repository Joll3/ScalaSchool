
# This assignment asks you to find the most frequent value in a data array.
        
# Namely, you must find the most frequently occurring value in a data
# array that starts at memory address $0 and contains $1 words.
# The most frequent value must be stored in register $2.
# Complete and submit the part delimited by "----" and indicated
# by a "nop" below.

# Here is some wrapper code to test your solution:

        mov     $0, >test_data  # set up the address where to get the data
        mov     $1, >test_len   # set up address where to get the length
        loa     $1, $1          # load the length from memory to $1

# Your solution starts here ...
# ------------------------------------------
	  mov $7, $1
      add $1, $1, $0				
      sub $1, $1, 1           # address of the last data item
@select_loop:
      cmp $0, $1              # compare start address and last address
      bae >frequent_sort               # ... if start addr >= last addr, we are done
      loa $2, $0              # set up a candidate maximum
      mov $3, $0              # set up address of candidate maximum
      add $4, $0, 1           # set up current address
@max_scan_loop:
      cmp $4, $1              # compare current address with last address
      bab >scan_done          # ... if curr addr > last addr, we have the max
      loa $5, $4              # load current data item
      cmp $5, $2              # compare current item with candidate max
      bbe >no_update          # ... if current <= candidate, no need to update
      mov $2, $5              # update candidate maximum
      mov $3, $4              # update address of candidate maximum
@no_update:
      add $4, $4, 1           # advance to next element
      jmp >max_scan_loop      # continue scanning
@scan_done:
      # at this point $2 is the max item and $3 is its addr in array
      # transpose max item and last item in current array ...
      sub $4, $4, 1           # address of last item
      loa $5, $4              # load last item
      sto $4, $2              # store max to last position
      sto $3, $5              # store last item to max position
      sub $1, $1, 1           # remove last item (now =max) from consideration
      jmp >select_loop        # continue sorting the remaining array
      
#########################
@frequent_sort:         	 	#	$6 frequency of current mfrq
    add $1, $7, $0
    sub $1, $1, 1           	# last address
    mov $7, 0             

@select_loop2:
      cmp $0, $1              # compare start to last address
      bae >done               # ... if start addr >= last addr, we are done
      loa $2, $0              # set up a candidate most frequent number mfrq
      mov $3, $0              # set up address of candidate
      add $4, $0, 1           # set up current address
      loa $0, $0

@mfrq_scan_loop:
      cmp $4, $1              # compare current w last address
      bab >done         		 # ... if curr addr > last addr, we are finished
      loa $5, $4              # load current data item
      cmp $5, $0              # compare current item with previous itema
      bne >new         		# ... if current != candidate, it's a new number
      add $7, $7, 1           #frequency of mfrq candidate += 1
      cmp $7, $6              #if candidate's frequency > the current mfrq -- > new mfrq
      bab >new_mfn			 
      add $4, $4, 1           # advance
      jmp >mfrq_scan_loop

@new:
      loa $0, $4
      add $4, $4, 1           
      mov $7, 1          	 # reset 7
      jmp >mfrq_scan_loop     

@new_mfn:
      loa $0, $4
      mov $2, $5                
      mov $6, $7                # candidate mfrq frequency replaces the previous mfrq
      mov $3, $4                # update address of the new mfrq
      add $4, $4, 1             # advance
      jmp >mfrq_scan_loop

@done:
# ------------------------------------------
# ... and ends here 

        hlt                     # the processor stops here

# Here is the test data:
# (the most frequent value is 56369 with frequency 5 in the array)

@test_len:
        %data   23
@test_data:
        %data   18701, 1100, 590, 17017, 56369, 19296, 1100, 56369, 1100, 56369, 590, 18701, 19296, 19296, 56369, 1100, 55034, 590, 29135, 18701, 18701, 29135, 56369

