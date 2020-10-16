/************************************************************
*
*       Filename        :       Mux2.v
*       Revision        :       first version
*       Description     :       select which to output between two input data
*
*       sel             :       input, control, sel == 0, output in0 else output in1
*       in0             :       input[DWIDTH-1:0], data, input data 0
*       in1             :       input[DWIDTH-1:0], data, input data 1
*       out             :       output[DWIDTH-1:0], data, output data
*
************************************************************/
module Mux2 #(parameter DWIDTH = 6)(
  input sel,
  input [DWIDTH-1:0] in0,
  input [DWIDTH-1:0] in1,
  output [DWIDTH-1:0] out
);

  assign out = sel ? in0: in1;

endmodule
