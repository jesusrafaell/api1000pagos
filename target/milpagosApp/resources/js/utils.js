//Valida solo numeros
function digitos(event){
  if (window.event) {
    // IE
    key = event.keyCode;
  } 
  else if (event.which) {
    // netscape
    key = event.which;
  }
  if (key != 8 || key != 13 || key < 48 || key > 57)
    return (((key > 47) && (key < 58)) || (key == 8 ) || (key == 13));
    return true;
}

//Formato para montos
function puntitos(donde,caracter,campo)
{
var decimales = false
campo = eval("donde.form." + campo)
dec = 2
if (dec != 0)
{decimales = true}

pat = /[\*,\+,\(,\),\?,\\,\$,\[,\],\^]/
valor = donde.value
largo = valor.length
crtr = true
if(isNaN(caracter) || pat.test(caracter) == true)
	{
	if (pat.test(caracter)==true) 
		{caracter = "\\" + caracter}
	carcter = new RegExp(caracter,"g")
	valor = valor.replace(carcter,"")
	donde.value = valor
	crtr = false
	}
else
	{
	var nums = new Array()
	cont = 0
	for(m=0;m<largo;m++)
		{
		if(valor.charAt(m) == "." || valor.charAt(m) == " " || valor.charAt(m) == ",")
			{continue;}
		else{
			nums[cont] = valor.charAt(m)
			cont++
			}
		
		}
	}

if(decimales == true) {
	ctdd = eval(1 + dec);
	nmrs = 1
}
else {
	ctdd = 1; nmrs = 3
}
var cad1="",cad2="",cad3="",tres=0
valor_sincoma = valor.replace(",","")
largo=valor_sincoma.length
if(largo > nmrs && crtr == true)
	{	
	for (k=nums.length-ctdd;k>=0;k--){
		cad1 = nums[k]
		cad2 = cad1 + cad2
		tres++
		if((tres%3) == 0){
			if(k!=0){
				cad2 = "." + cad2
				}
			}
		}
		
	for (dd = dec; dd > 0; dd--)	
	{cad3 += nums[nums.length-dd] }
	if(decimales == true)
	{cad2 += "," + cad3}
	 donde.value = cad2
	}
donde.focus()
}
