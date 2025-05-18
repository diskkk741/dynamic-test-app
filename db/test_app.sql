-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Anamakine: 127.0.0.1:3309
-- Üretim Zamanı: 18 May 2025, 22:53:59
-- Sunucu sürümü: 10.4.32-MariaDB
-- PHP Sürümü: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Veritabanı: `test_app`
--

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `questions`
--

CREATE TABLE `questions` (
  `id` int(11) NOT NULL,
  `question_text` text NOT NULL,
  `option_a` varchar(255) DEFAULT NULL,
  `option_b` varchar(255) DEFAULT NULL,
  `option_c` varchar(255) DEFAULT NULL,
  `option_d` varchar(255) DEFAULT NULL,
  `correct_answer` varchar(1) DEFAULT NULL,
  `explanation` text DEFAULT NULL,
  `difficulty` enum('easy','medium','hard') DEFAULT 'medium'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `questions`
--

INSERT INTO `questions` (`id`, `question_text`, `option_a`, `option_b`, `option_c`, `option_d`, `correct_answer`, `explanation`, `difficulty`) VALUES
(34, 'What is 2 + 2?', '3', '4', '5', '6', 'B', '2 + 2 = 4', 'easy'),
(35, 'What color is the sky on a clear day?', 'Green', 'Blue', 'Red', 'Yellow', 'B', 'The sky appears blue due to Rayleigh scattering.', 'easy'),
(36, 'Which day comes after Monday?', 'Sunday', 'Tuesday', 'Friday', 'Wednesday', 'B', 'Tuesday follows Monday.', 'easy'),
(37, 'What is the capital of France?', 'Berlin', 'Madrid', 'Paris', 'Rome', 'C', 'The capital of France is Paris.', 'easy'),
(38, 'What is 5 - 3?', '3', '2', '1', '4', 'B', '5 minus 3 equals 2.', 'easy'),
(39, 'Which planet is known as the Red Planet?', 'Earth', 'Venus', 'Mars', 'Jupiter', 'C', 'Mars is known as the Red Planet.', 'easy'),
(40, 'How many legs does a cat have?', '2', '3', '4', '5', 'C', 'Cats have 4 legs.', 'easy'),
(41, 'What do we drink when we are thirsty?', 'Water', 'Oil', 'Juice', 'Vinegar', 'A', 'Water quenches thirst.', 'easy'),
(42, 'What is the opposite of hot?', 'Warm', 'Cold', 'Cool', 'Boiling', 'B', 'The opposite of hot is cold.', 'easy'),
(43, 'What is the shape of a wheel?', 'Square', 'Rectangle', 'Circle', 'Triangle', 'C', 'Wheels are circular.', 'easy'),
(44, 'What is 10 - 7?', '4', '2', '3', '1', 'C', '10 minus 7 is 3.', 'easy'),
(45, 'Which animal says \'meow\'?', 'Dog', 'Cat', 'Cow', 'Bird', 'B', 'Cats say meow.', 'easy'),
(46, 'What is the color of a banana?', 'Red', 'Green', 'Yellow', 'Blue', 'C', 'Bananas are yellow.', 'easy'),
(47, 'Which number comes after 4?', '3', '5', '6', '2', 'B', '5 comes after 4.', 'easy'),
(48, 'How many eyes does a human have?', '1', '2', '3', '4', 'B', 'Humans have 2 eyes.', 'easy'),
(49, 'What is 3 + 5?', '7', '8', '9', '6', 'B', '3 + 5 = 8', 'easy'),
(50, 'What color is grass?', 'Blue', 'Green', 'Yellow', 'Red', 'B', 'Grass is green.', 'easy'),
(51, 'What is used to write on paper?', 'Spoon', 'Pen', 'Fork', 'Comb', 'B', 'We use a pen to write.', 'easy'),
(52, 'Which season is the coldest?', 'Spring', 'Summer', 'Winter', 'Autumn', 'C', 'Winter is coldest.', 'easy'),
(53, 'What is 6 divided by 2?', '2', '3', '4', '5', 'B', '6 / 2 = 3', 'easy'),
(54, 'How many days are there in a week?', '5', '6', '7', '8', 'C', 'There are 7 days in a week.', 'easy'),
(55, 'What is the first letter of the English alphabet?', 'B', 'C', 'A', 'D', 'C', 'A is the first letter.', 'easy'),
(56, 'What do bees make?', 'Milk', 'Cheese', 'Honey', 'Butter', 'C', 'Bees make honey.', 'easy'),
(57, 'How many fingers does a human hand have?', '3', '4', '5', '6', 'C', '5 fingers on a hand.', 'easy'),
(58, 'What is the color of blood?', 'Green', 'Blue', 'Red', 'White', 'C', 'Blood is red.', 'easy'),
(59, 'How many wheels does a bicycle have?', '1', '2', '3', '4', 'B', 'Bicycles have 2 wheels.', 'easy'),
(60, 'What is 9 + 1?', '8', '9', '10', '11', 'C', '9 + 1 = 10', 'easy'),
(61, 'Which one is a fruit?', 'Carrot', 'Potato', 'Apple', 'Onion', 'C', 'Apple is a fruit.', 'easy'),
(62, 'Where does fish live?', 'Land', 'Sky', 'Water', 'Tree', 'C', 'Fish live in water.', 'easy'),
(63, 'What is 4 x 2?', '6', '7', '8', '9', 'C', '4 times 2 is 8.', 'easy'),
(64, 'Which sense is used for seeing?', 'Taste', 'Touch', 'Sight', 'Hearing', 'C', 'Sight is for seeing.', 'easy'),
(65, 'Which is a mammal?', 'Frog', 'Snake', 'Elephant', 'Lizard', 'C', 'Elephant is a mammal.', 'easy'),
(66, 'How many ears does a human have?', '1', '2', '3', '4', 'B', 'Humans have 2 ears.', 'easy'),
(67, 'What color is a strawberry?', 'Green', 'Red', 'Blue', 'Purple', 'B', 'Strawberries are red.', 'easy'),
(68, 'Which is bigger?', 'Mouse', 'Cat', 'Dog', 'Elephant', 'D', 'Elephant is the biggest.', 'easy'),
(69, 'What is 1 + 1?', '1', '2', '3', '4', 'B', '1 + 1 = 2', 'easy'),
(70, 'What is used to tell time?', 'Spoon', 'Watch', 'Pencil', 'Knife', 'B', 'We use a watch.', 'easy'),
(71, 'Which month comes after June?', 'May', 'July', 'August', 'April', 'B', 'July comes after June.', 'easy'),
(72, 'Which is an insect?', 'Dog', 'Cat', 'Bee', 'Fish', 'C', 'Bee is an insect.', 'easy'),
(73, 'What do you wear on your feet?', 'Gloves', 'Hat', 'Shoes', 'Scarf', 'C', 'Shoes go on feet.', 'easy'),
(74, 'How many letters in the word \'dog\'?', '2', '3', '4', '5', 'B', 'Dog has 3 letters.', 'easy'),
(75, 'Which is a color?', 'Fast', 'Red', 'Jump', 'Slow', 'B', 'Red is a color.', 'easy'),
(76, 'How many hours in a day?', '12', '18', '24', '36', 'C', '24 hours in a day.', 'easy'),
(77, 'Which is a vegetable?', 'Banana', 'Apple', 'Carrot', 'Orange', 'C', 'Carrot is a vegetable.', 'easy'),
(78, 'Which organ is used to pump blood?', 'Brain', 'Lung', 'Heart', 'Liver', 'C', 'Heart pumps blood.', 'easy'),
(79, 'What is the name of our planet?', 'Mars', 'Earth', 'Sun', 'Moon', 'B', 'We live on Earth.', 'easy'),
(80, 'Which is used to cut things?', 'Fork', 'Knife', 'Plate', 'Cup', 'B', 'Knives cut things.', 'easy'),
(81, 'What is the color of coal?', 'White', 'Black', 'Red', 'Green', 'B', 'Coal is black.', 'easy'),
(82, 'What is 7 - 2?', '4', '5', '6', '3', 'B', '7 minus 2 is 5.', 'easy'),
(83, 'What do we breathe in?', 'Smoke', 'Oxygen', 'Water', 'Dust', 'B', 'We breathe oxygen.', 'easy'),
(84, 'What is the square root of 144?', '10', '12', '14', '16', 'B', '√144 = 12', 'medium'),
(85, 'Who wrote the play \"Romeo and Juliet\"?', 'Charles Dickens', 'William Shakespeare', 'Jane Austen', 'Mark Twain', 'B', 'The play was written by William Shakespeare.', 'medium'),
(86, 'Which planet is known as the Red Planet?', 'Venus', 'Mars', 'Jupiter', 'Saturn', 'B', 'Mars is known as the Red Planet due to iron oxide on its surface.', 'medium'),
(87, 'What is the chemical symbol for Sodium?', 'Na', 'S', 'Sn', 'N', 'A', 'Na stands for Sodium in the periodic table.', 'medium'),
(88, 'How many continents are there on Earth?', '5', '6', '7', '8', 'C', 'There are 7 continents on Earth.', 'medium'),
(89, 'What is the capital of Canada?', 'Toronto', 'Vancouver', 'Montreal', 'Ottawa', 'D', 'Ottawa is the capital of Canada.', 'medium'),
(90, 'What is 15% of 200?', '25', '30', '35', '40', 'B', '15% of 200 is (15/100)*200 = 30.', 'medium'),
(91, 'Who developed the theory of relativity?', 'Newton', 'Einstein', 'Bohr', 'Tesla', 'B', 'Einstein proposed the theory of relativity.', 'medium'),
(92, 'Which ocean is the deepest?', 'Atlantic', 'Arctic', 'Indian', 'Pacific', 'D', 'The Pacific Ocean is the deepest.', 'medium'),
(93, 'Which country has the largest population?', 'USA', 'India', 'Russia', 'China', 'D', 'China has the largest population.', 'medium'),
(94, 'What gas do plants absorb from the atmosphere?', 'Oxygen', 'Carbon Dioxide', 'Nitrogen', 'Hydrogen', 'B', 'Plants absorb CO₂ during photosynthesis.', 'medium'),
(95, 'What is the capital of Brazil?', 'Brasília', 'Rio de Janeiro', 'São Paulo', 'Salvador', 'A', 'Brasília is the capital of Brazil.', 'medium'),
(96, 'How many degrees are in a right angle?', '45', '60', '90', '120', 'C', 'A right angle is 90 degrees.', 'medium'),
(97, 'Who painted the Mona Lisa?', 'Van Gogh', 'Picasso', 'Da Vinci', 'Michelangelo', 'C', 'Da Vinci painted the Mona Lisa.', 'medium'),
(98, 'Which language has the most native speakers?', 'English', 'Spanish', 'Mandarin Chinese', 'Hindi', 'C', 'Mandarin has the most native speakers.', 'medium'),
(99, 'Which metal is liquid at room temperature?', 'Gold', 'Silver', 'Mercury', 'Lead', 'C', 'Mercury is the only metal liquid at room temp.', 'medium'),
(100, 'What is the boiling point of water in Celsius?', '90', '100', '110', '120', 'B', 'Water boils at 100°C.', 'medium'),
(101, 'Which continent is Egypt located in?', 'Asia', 'Europe', 'Africa', 'Australia', 'C', 'Egypt is in Africa.', 'medium'),
(102, 'Which blood type is the universal donor?', 'A', 'B', 'AB', 'O', 'D', 'O negative is the universal donor.', 'medium'),
(103, 'What is the largest bone in the human body?', 'Femur', 'Tibia', 'Spine', 'Humerus', 'A', 'Femur is the largest bone.', 'medium'),
(104, 'How many legs does a spider have?', '6', '8', '10', '12', 'B', 'Spiders have 8 legs.', 'medium'),
(105, 'Which part of the brain controls breathing?', 'Cerebellum', 'Cerebrum', 'Medulla', 'Hypothalamus', 'C', 'Medulla controls breathing.', 'medium'),
(106, 'Who invented the telephone?', 'Einstein', 'Newton', 'Alexander Graham Bell', 'Edison', 'C', 'Bell invented the telephone.', 'medium'),
(107, 'What is the most abundant gas in Earth’s atmosphere?', 'Oxygen', 'Hydrogen', 'Carbon Dioxide', 'Nitrogen', 'D', 'Nitrogen is most abundant.', 'medium'),
(108, 'What is the smallest planet in our solar system?', 'Mercury', 'Mars', 'Venus', 'Pluto', 'A', 'Mercury is the smallest.', 'medium'),
(109, 'Which year did World War II end?', '1940', '1943', '1945', '1950', 'C', 'WWII ended in 1945.', 'medium'),
(110, 'What is the freezing point of water in Fahrenheit?', '32°F', '0°F', '100°F', '212°F', 'A', 'Water freezes at 32°F.', 'medium'),
(111, 'What number is represented by the Roman numeral X?', '5', '10', '15', '20', 'B', 'X = 10 in Roman numerals.', 'medium'),
(112, 'What is the capital city of Japan?', 'Osaka', 'Kyoto', 'Tokyo', 'Hiroshima', 'C', 'Tokyo is the capital of Japan.', 'medium'),
(113, 'What is the currency of the UK?', 'Euro', 'Pound', 'Dollar', 'Franc', 'B', 'UK uses the Pound Sterling.', 'medium'),
(114, 'What color do you get when you mix red and blue?', 'Green', 'Orange', 'Purple', 'Brown', 'C', 'Red + Blue = Purple.', 'medium'),
(115, 'Which is the longest river in the world?', 'Amazon', 'Nile', 'Yangtze', 'Mississippi', 'B', 'Nile is longest.', 'medium'),
(116, 'What is the hardest natural substance?', 'Gold', 'Iron', 'Diamond', 'Quartz', 'C', 'Diamond is hardest.', 'medium'),
(117, 'How many players are on a football team (soccer)?', '9', '10', '11', '12', 'C', '11 players per team.', 'medium'),
(118, 'Who discovered penicillin?', 'Pasteur', 'Fleming', 'Curie', 'Newton', 'B', 'Fleming discovered penicillin.', 'medium'),
(119, 'Which country is known for the Eiffel Tower?', 'Italy', 'Germany', 'France', 'Spain', 'C', 'France is home to Eiffel Tower.', 'medium'),
(120, 'What do bees collect and use to make honey?', 'Water', 'Pollen', 'Nectar', 'Sap', 'C', 'Bees use nectar to make honey.', 'medium'),
(121, 'What is 9 x 7?', '56', '63', '72', '49', 'B', '9×7 = 63.', 'medium'),
(122, 'What is the capital of Australia?', 'Sydney', 'Canberra', 'Melbourne', 'Perth', 'B', 'Canberra is the capital.', 'medium'),
(123, 'Which gas do humans need to breathe?', 'CO2', 'Nitrogen', 'Oxygen', 'Hydrogen', 'C', 'Humans breathe oxygen.', 'medium'),
(124, 'Who painted The Starry Night?', 'Van Gogh', 'Da Vinci', 'Monet', 'Picasso', 'A', 'Van Gogh painted it.', 'medium'),
(125, 'Which is not a prime number?', '3', '5', '7', '9', 'D', '9 is not prime.', 'medium'),
(126, 'What is H2O commonly known as?', 'Salt', 'Water', 'Hydrogen', 'Oxygen', 'B', 'H₂O = Water.', 'medium'),
(127, 'Which sport uses a shuttlecock?', 'Tennis', 'Football', 'Badminton', 'Basketball', 'C', 'Badminton uses shuttlecock.', 'medium'),
(128, 'Who is the author of Harry Potter?', 'J.K. Rowling', 'Tolkien', 'C.S. Lewis', 'Suzanne Collins', 'A', 'J.K. Rowling wrote HP.', 'medium'),
(129, 'What is the capital of Italy?', 'Rome', 'Venice', 'Milan', 'Naples', 'A', 'Rome is the capital.', 'medium'),
(130, 'Which element has the atomic number 1?', 'Oxygen', 'Hydrogen', 'Carbon', 'Helium', 'B', 'Hydrogen is 1.', 'medium'),
(131, 'What’s the smallest unit of life?', 'Organ', 'Cell', 'Tissue', 'Molecule', 'B', 'Cells are the smallest living units.', 'medium'),
(132, 'How many hours are in a week?', '168', '144', '156', '160', 'A', '7×24 = 168 hours.', 'medium'),
(133, 'How many teeth does a normal adult human have?', '28', '30', '32', '34', 'C', '32 teeth total.', 'medium'),
(134, 'What is the derivative of sin(x)?', 'cos(x)', '-cos(x)', 'sin(x)', '-sin(x)', 'A', 'The derivative of sin(x) is cos(x).', 'hard'),
(135, 'Who developed the general theory of relativity?', 'Isaac Newton', 'Albert Einstein', 'Galileo Galilei', 'Nikola Tesla', 'B', 'Einstein developed general relativity.', 'hard'),
(136, 'What year did the French Revolution begin?', '1789', '1776', '1804', '1799', 'A', 'French Revolution started in 1789.', 'hard'),
(137, 'Which element has the atomic number 92?', 'Uranium', 'Plutonium', 'Radium', 'Thorium', 'A', 'Uranium has atomic number 92.', 'hard'),
(138, 'What is the integral of 1/x dx?', 'ln(x)', 'x', '1/x²', 'x²/2', 'A', '∫1/x dx = ln(x)', 'hard'),
(139, 'What language is most similar to Latin?', 'Spanish', 'Romanian', 'Italian', 'French', 'C', 'Italian is the closest modern language to Latin.', 'hard'),
(140, 'Which country has the most official languages?', 'India', 'Switzerland', 'South Africa', 'Belgium', 'C', 'South Africa has 11 official languages.', 'hard'),
(141, 'Who won the Nobel Prize in Physics in 1921?', 'Einstein', 'Bohr', 'Fermi', 'Heisenberg', 'A', 'Einstein won it for the photoelectric effect.', 'hard'),
(142, 'What is Schrödinger’s cat a paradox of?', 'Quantum mechanics', 'Relativity', 'Biology', 'Optics', 'A', 'It’s a thought experiment in quantum theory.', 'hard'),
(143, 'What is the square root of 1024?', '30', '32', '36', '40', 'B', '32² = 1024', 'hard'),
(144, 'Which organ produces insulin?', 'Liver', 'Kidney', 'Pancreas', 'Heart', 'C', 'Insulin is produced in the pancreas.', 'hard'),
(145, 'What is the capital of Kazakhstan?', 'Astana', 'Almaty', 'Baku', 'Tashkent', 'A', 'Astana is the capital.', 'hard'),
(146, 'What is the speed of light in vacuum?', '3×10⁸ m/s', '2×10⁸ m/s', '1.5×10⁸ m/s', '2.5×10⁸ m/s', 'A', 'Speed of light ≈ 3×10⁸ m/s.', 'hard'),
(147, 'Which philosopher wrote \"Critique of Pure Reason\"?', 'Descartes', 'Kant', 'Hume', 'Locke', 'B', 'Immanuel Kant wrote it.', 'hard'),
(148, 'What is the name of the longest bone in the human body?', 'Humerus', 'Femur', 'Tibia', 'Fibula', 'B', 'Femur is the longest.', 'hard'),
(149, 'What year did World War I begin?', '1914', '1917', '1939', '1905', 'A', 'WWI started in 1914.', 'hard'),
(150, 'What is the capital of Myanmar?', 'Yangon', 'Naypyidaw', 'Mandalay', 'Bago', 'B', 'Naypyidaw is the capital.', 'hard'),
(151, 'Which enzyme breaks down proteins in the stomach?', 'Amylase', 'Lipase', 'Pepsin', 'Trypsin', 'C', 'Pepsin digests protein.', 'hard'),
(152, 'What is the smallest unit of data in computing?', 'Byte', 'Bit', 'Nibble', 'Word', 'B', 'Bit is smallest.', 'hard'),
(153, 'Who discovered radioactivity?', 'Curie', 'Einstein', 'Rutherford', 'Becquerel', 'D', 'Henri Becquerel discovered it.', 'hard'),
(154, 'What is the capital of Bhutan?', 'Thimphu', 'Kathmandu', 'Lhasa', 'Colombo', 'A', 'Thimphu is the capital.', 'hard'),
(155, 'What is the formula for kinetic energy?', '½mv²', 'mv', 'mg', 'v²/2', 'A', 'KE = ½mv²', 'hard'),
(156, 'Who painted the ceiling of the Sistine Chapel?', 'Da Vinci', 'Raphael', 'Michelangelo', 'Donatello', 'C', 'Michelangelo painted it.', 'hard'),
(157, 'What is the chemical formula for ozone?', 'O', 'O₂', 'O₃', 'O₄', 'C', 'O₃ is ozone.', 'hard'),
(158, 'Which war was fought from 1950–1953?', 'Vietnam', 'WWII', 'Korean', 'Gulf', 'C', 'Korean War was from 1950–53.', 'hard'),
(159, 'Who wrote \"The Brothers Karamazov\"?', 'Tolstoy', 'Dostoevsky', 'Chekhov', 'Turgenev', 'B', 'Dostoevsky wrote it.', 'hard'),
(160, 'Which particle has no electric charge?', 'Proton', 'Neutron', 'Electron', 'Positron', 'B', 'Neutron is neutral.', 'hard'),
(161, 'What is the second law of thermodynamics?', 'Entropy increases', 'Energy is conserved', 'F = ma', 'E = mc²', 'A', 'Entropy always increases.', 'hard'),
(162, 'Which planet has the shortest day?', 'Earth', 'Jupiter', 'Venus', 'Saturn', 'B', 'Jupiter’s day is about 10 hours.', 'hard'),
(163, 'What is the powerhouse of the cell?', 'Nucleus', 'Mitochondria', 'Ribosome', 'Golgi body', 'B', 'Mitochondria produce energy.', 'hard'),
(164, 'Which year did the Berlin Wall fall?', '1985', '1987', '1989', '1991', 'C', 'Wall fell in 1989.', 'hard'),
(165, 'Who formulated the laws of motion?', 'Einstein', 'Galileo', 'Newton', 'Tesla', 'C', 'Newton did.', 'hard'),
(166, 'Which metal has the highest melting point?', 'Iron', 'Tungsten', 'Gold', 'Platinum', 'B', 'Tungsten melts at ~3422°C.', 'hard'),
(167, 'What is the capital of Slovakia?', 'Bratislava', 'Ljubljana', 'Zagreb', 'Sofia', 'A', 'Bratislava is the capital.', 'hard'),
(168, 'Which gas is used in fluorescent lamps?', 'Oxygen', 'Argon', 'Nitrogen', 'Carbon Dioxide', 'B', 'Argon is used.', 'hard'),
(169, 'What is 111 binary in decimal?', '6', '7', '8', '9', 'B', '111₂ = 7₁₀.', 'hard'),
(170, 'What does DNA stand for?', 'Deoxyribonucleic Acid', 'Dioxyribose Acid', 'Dinucleic Acid', 'Double Nucleotide Acid', 'A', 'DNA = Deoxyribonucleic Acid.', 'hard'),
(171, 'What is the SI unit of force?', 'Newton', 'Joule', 'Watt', 'Pascal', 'A', 'Force is measured in Newtons.', 'hard'),
(172, 'Who is the father of modern chemistry?', 'Dalton', 'Lavoisier', 'Mendeleev', 'Boyle', 'B', 'Lavoisier is considered the father.', 'hard'),
(173, 'What is the hardest known natural material?', 'Diamond', 'Graphite', 'Quartz', 'Obsidian', 'A', 'Diamond is hardest.', 'hard'),
(174, 'What is the pH of a neutral solution?', '0', '7', '10', '14', 'B', 'Neutral pH is 7.', 'hard'),
(175, 'Which organ filters blood in the human body?', 'Heart', 'Liver', 'Kidney', 'Lungs', 'C', 'Kidneys filter blood.', 'hard'),
(176, 'Which gas is most commonly used in fire extinguishers?', 'Oxygen', 'Carbon Dioxide', 'Hydrogen', 'Nitrogen', 'B', 'CO₂ is used in extinguishers.', 'hard'),
(177, 'Which ocean is the smallest?', 'Indian', 'Arctic', 'Atlantic', 'Southern', 'B', 'Arctic is smallest.', 'hard'),
(178, 'What is the largest internal organ?', 'Liver', 'Heart', 'Lungs', 'Stomach', 'A', 'Liver is largest.', 'hard'),
(179, 'What color is cobalt chloride when dry?', 'Blue', 'Red', 'Pink', 'Green', 'B', 'It’s blue when hydrated, pink when dry.', 'hard'),
(180, 'What number does a byte represent?', '8 bits', '16 bits', '4 bits', '2 bits', 'A', '1 byte = 8 bits.', 'hard'),
(181, 'Which vitamin is produced in skin by sunlight?', 'A', 'B', 'C', 'D', 'D', 'Vitamin D is synthesized with sunlight.', 'hard'),
(182, 'Which is not a programming language?', 'Python', 'Java', 'Ruby', 'Hadoop', 'D', 'Hadoop is a framework.', 'hard'),
(183, 'How many chromosomes in human cells?', '42', '44', '46', '48', 'C', 'Humans have 46 chromosomes.', 'hard');

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `test_analytics`
--

CREATE TABLE `test_analytics` (
  `id` int(11) NOT NULL,
  `result_id` int(11) NOT NULL,
  `correct_easy` int(11) DEFAULT 0,
  `wrong_easy` int(11) DEFAULT 0,
  `correct_medium` int(11) DEFAULT 0,
  `wrong_medium` int(11) DEFAULT 0,
  `correct_hard` int(11) DEFAULT 0,
  `wrong_hard` int(11) DEFAULT 0,
  `total_time_sec` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `test_analytics`
--

INSERT INTO `test_analytics` (`id`, `result_id`, `correct_easy`, `wrong_easy`, `correct_medium`, `wrong_medium`, `correct_hard`, `wrong_hard`, `total_time_sec`) VALUES
(9, 15, 5, 3, 2, 1, 0, 1, 45),
(11, 17, 5, 6, 4, 5, 1, 4, 0);

-- --------------------------------------------------------

--
-- Tablo için tablo yapısı `test_results`
--

CREATE TABLE `test_results` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `total` int(11) NOT NULL,
  `completed_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Tablo döküm verisi `test_results`
--

INSERT INTO `test_results` (`id`, `user_id`, `score`, `total`, `completed_at`) VALUES
(15, 1, 9, 25, '2025-05-18 20:08:20'),
(16, 1, 7, 25, '2025-05-18 20:19:06'),
(17, 1, 10, 25, '2025-05-18 23:48:20');

--
-- Dökümü yapılmış tablolar için indeksler
--

--
-- Tablo için indeksler `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`id`);

--
-- Tablo için indeksler `test_analytics`
--
ALTER TABLE `test_analytics`
  ADD PRIMARY KEY (`id`),
  ADD KEY `result_id` (`result_id`);

--
-- Tablo için indeksler `test_results`
--
ALTER TABLE `test_results`
  ADD PRIMARY KEY (`id`);

--
-- Dökümü yapılmış tablolar için AUTO_INCREMENT değeri
--

--
-- Tablo için AUTO_INCREMENT değeri `questions`
--
ALTER TABLE `questions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=184;

--
-- Tablo için AUTO_INCREMENT değeri `test_analytics`
--
ALTER TABLE `test_analytics`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- Tablo için AUTO_INCREMENT değeri `test_results`
--
ALTER TABLE `test_results`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Dökümü yapılmış tablolar için kısıtlamalar
--

--
-- Tablo kısıtlamaları `test_analytics`
--
ALTER TABLE `test_analytics`
  ADD CONSTRAINT `test_analytics_ibfk_1` FOREIGN KEY (`result_id`) REFERENCES `test_results` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
