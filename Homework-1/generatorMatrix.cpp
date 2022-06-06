#include <bits/stdc++.h>
#include <algorithm>
#include <vector>
#define sz(x) (int)x.size()
#define pb push_back
#define maxi 30
#define prob 950
using namespace std;



int main() {
	ios::sync_with_stdio(false);
	cin.tie(0);
	int numberfile;
	cin>>numberfile;
	while(numberfile--){	
		int x=rand() % maxi + 2;
		int y=rand() % maxi + 2;
		cout<<x<<"  "<<y<<endl;
		fstream my_file;
		string filename="fileData";
		filename+=to_string(numberfile);
		filename+=".dzn";  
		cout<<filename;
		my_file.open(filename, ios::out);
		if (!my_file) {
			cout << "File not created!";
		}
		else {
			my_file<< "R=";
			my_file<< x;
			my_file<< ";\nC=";
			my_file<< y;
			my_file<< ";\nbomb=[|";

			cout << "File created successfully!";
			for(int i=0;i<x;i++){
				for(int j=0;j<y;j++){
					int valore=rand() % 1000 ;
					//cout<<valore<<"\n";
					if(valore< prob){
						my_file << "0";
					}else my_file << "M";
					my_file << ",";
				}
				if(i!=x-1) my_file<< "|\n";
			}
			my_file<<"|];";
			my_file.close();
		}
	}


}
